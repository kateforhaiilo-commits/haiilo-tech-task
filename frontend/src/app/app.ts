import { Component, inject, OnInit } from '@angular/core';
import { CheckoutComponent } from './supermarket/checkout/checkout';
import { ItemsComponent } from './supermarket/items/items';
import { HeaderComponent } from './supermarket/header/header';
import { CheckoutStore } from './store/checkout.store';

@Component({
  selector: 'app-root',
  imports: [HeaderComponent, ItemsComponent, CheckoutComponent],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App implements OnInit {
  private readonly store = inject(CheckoutStore);

  public ngOnInit(): void {
    this.store.loadItems();
  }
}
