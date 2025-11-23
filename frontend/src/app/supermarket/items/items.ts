import { CommonModule } from '@angular/common';
import { Component, effect, inject, OnDestroy } from '@angular/core';
import { CheckoutStore, Item } from '../../store/checkout.store';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { pairwise, startWith, Subscription } from 'rxjs';

@Component({
  selector: 'items',
  imports: [ CommonModule, ReactiveFormsModule ],
  templateUrl: './items.html',
  styleUrl: './items.scss',
})
export class ItemsComponent implements OnDestroy {
  public readonly store = inject(CheckoutStore);
  private readonly fb = inject(FormBuilder);
  public items = this.store.items;
  public itemsForm: FormGroup;
  private formSubscriptions = new Map<string, Subscription>();

  constructor() {
    this.itemsForm = this.fb.group({});
    effect(() => {
      const currentItems = this.store.items();
      const currentControls = Object.keys(this.itemsForm.controls);
      const itemNames = currentItems.map(item => item.name);

      // Add new controls for new items
      currentItems.forEach((item: Item) => {
        if (!this.itemsForm.contains(item.name)) {
          const control = this.fb.control(item.quantity || 0, [
            Validators.min(0),
            Validators.max(999),
            Validators.pattern(/^[0-9]*$/) // only whole numbers
          ]);
          this.itemsForm.addControl(item.name, control);

          const sub = control.valueChanges.pipe(
            startWith(control.value),
            pairwise()
          ).subscribe(([prev, next]) => {
            // special null check to avoid problems with the value of 0
            // todo add invalid styling and error messages to the form inputs
            if (control.valid && prev !== next && next !== null) {
              this.store.updateItemQuantity(item.name, next);
            } else if (!control.valid && prev !== next && next !== null) {
              // if value is invalid -> reset to 0 in the store
              this.store.updateItemQuantity(item.name, 0);
            }
          });
          this.formSubscriptions.set(item.name, sub);
        }
      });

      // Remove old controls that are no longer in the items list
      currentControls.forEach(controlName => {
        if (!itemNames.includes(controlName)) {
          this.itemsForm.removeControl(controlName);
          this.formSubscriptions.get(controlName)?.unsubscribe();
          this.formSubscriptions.delete(controlName);
        }
      });
    });
  }

  /**
   * Cleanup subscriptions on component destroy
   */
  public ngOnDestroy(): void {
    this.formSubscriptions.forEach(sub => sub.unsubscribe());
  }

  /**
   * Increments the quantity of the specified item in the form via button click.
   * @param itameName
   */
  public incrementQuantity(itameName: string): void {
    const control = this.itemsForm.get(itameName);

    if (control && control.valid && control.value < 999) {
      control.setValue(control.value + 1);
    }
  }

  /**
   * Decrements the quantity of the specified item in the form via button click.
   * @param itameName
   */
  public decrementQuantity(itameName: string): void {
    const control = this.itemsForm.get(itameName);

    if (control && control.valid && control.value > 0) {
      control.setValue(control.value - 1);
    }
  }
}
