import { Component } from '@angular/core';
import { CheckoutComponent } from './supermarket/checkout/checkout';
import { ItemsComponent } from './supermarket/items/items';
import { HeaderComponent } from './supermarket/header/header';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrl: './app.scss',
  imports: [HeaderComponent, ItemsComponent, CheckoutComponent],
  standalone: true
})
export class App {
}
