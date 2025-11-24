import { NgOptimizedImage } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'header',
  imports: [ NgOptimizedImage ],
  templateUrl: './header.html',
  styleUrl: './header.scss',
})
export class HeaderComponent {

}
