import { CommonModule } from '@angular/common';
import { Component, effect, inject, signal } from '@angular/core';
import { CheckoutStore, Item } from '../../store/checkout.store';

@Component({
  selector: 'checkout',
  imports: [ CommonModule ],
  templateUrl: './checkout.html',
  styleUrls: ['./checkout.scss'],
})
export class CheckoutComponent {
  public readonly store = inject(CheckoutStore);
  public items = this.store.items;
  public total = this.store.total;
  public isNoProductSelected = signal(true);

  public constructor() {
    effect(() => {
      const currentItems = this.store.items();
      this.isNoProductSelected.set(currentItems.every(item => item.quantity === 0));
    });
  }
}