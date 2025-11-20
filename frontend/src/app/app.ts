import { Component, inject, OnInit, signal } from '@angular/core';
import { CheckoutComponent } from './checkout/checkout';
import { ItemsComponent } from './items/items';
import { HeaderComponent } from './header/header';
import { HttpClient } from '@angular/common/http';
import { catchError, of } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrl: './app.scss',
  imports: [HeaderComponent, ItemsComponent, CheckoutComponent],
  standalone: true
})
export class App implements OnInit {
  private http = inject(HttpClient);
  public backendStatus = signal<'Checking...' | 'Online' | 'Offline'>('Checking...');

  public ngOnInit(): void {
    this.checkHealth();
  }

  // Simulate Backend Health Check
  public checkHealth(): void {
    this.backendStatus.set('Checking...');

    this.http.get<any>('http://localhost:8080/api/health').pipe(
      catchError((error) => {
        console.error('Connection failed:', error);
        return of({ status: 'DOWN' });
      })
    ).subscribe({
      next: (response) => {
        if (response && response.status === 'UP') {
          this.backendStatus.set('Online');
        } else {
          this.backendStatus.set('Offline');
        }
      },
      error: () => this.backendStatus.set('Offline')
    });
  }
}
