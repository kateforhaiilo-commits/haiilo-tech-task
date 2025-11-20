import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'checkout',
  imports: [ CommonModule ],
  templateUrl: './checkout.html',
  styleUrls: ['./checkout.scss'],
})
export class CheckoutComponent {
  public checkoutItems: Array<{name: string, price: number, amount: number}> = [{
    name: 'apple',
    price: 1.2,
    amount: 1
  }, {
    name: 'banana',
    price: 2.3,
    amount: 2
  }, {
    name: 'orange',
    price: 3.4,
    amount: 4
  }];

  public totalAmount(): number {
    const value = this.checkoutItems.reduce((total, item) => total + (item.price * item.amount), 0);
    console.log(value);

    return value;
  }
}