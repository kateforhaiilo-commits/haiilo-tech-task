import { CommonModule } from '@angular/common';
import { Component, effect, inject, signal } from '@angular/core';
import { CheckoutStore } from '../../store/checkout.store';

@Component({
  selector: 'app-checkout',
  imports: [CommonModule],
  templateUrl: './checkout.html',
})
export class CheckoutComponent {
  public readonly store = inject(CheckoutStore);
  public items = this.store.items;
  public total = this.store.total;
  public isNoProductSelected = signal(true);

  public constructor() {
    effect(() => {
      const currentItems = this.store.items();
      this.isNoProductSelected.set(currentItems.every((item) => item.quantity === 0));
    });
  }
}
