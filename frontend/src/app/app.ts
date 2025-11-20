import { Component, inject, OnInit } from '@angular/core';
import { CheckoutComponent } from './supermarket/checkout/checkout';
import { ItemsComponent } from './supermarket/items/items';
import { HeaderComponent } from './supermarket/header/header';
import { CheckoutStore } from './store/checkout.store';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrl: './app.scss',
  imports: [HeaderComponent, ItemsComponent, CheckoutComponent],
  standalone: true
})
export class App implements OnInit {
  public readonly store = inject(CheckoutStore);
  public items$ = this.store.items();

  public ngOnInit(): void {
    this.store.loadItems();
  }
}
