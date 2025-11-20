import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'items',
  imports: [ CommonModule ],
  templateUrl: './items.html',
  styleUrl: './items.scss',
})
export class ItemsComponent {
  public items: Array<{name: string, price: number}> = [{
    name: 'apple',
    price: 1.2
  }, {
    name: 'banana',
    price: 2.3
  }, {
    name: 'orange',
    price: 3.4
  }];
}
