import { Component, inject, signal } from '@angular/core';
import { CheckoutComponent } from './checkout/checkout';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrl: './app.scss',
  imports: [CheckoutComponent],
  standalone: true
})
export class App {
}
