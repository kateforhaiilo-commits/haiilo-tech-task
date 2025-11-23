import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
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
}