import { Component, signal, computed, inject, effect, OnInit } from '@angular/core';
import { CommonModule, NgOptimizedImage } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { delay, of, catchError } from 'rxjs';

@Component({
  selector: 'checkout',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './checkout.html',
  styleUrls: ['./checkout.scss'],
})
export class CheckoutComponent implements OnInit {
  private fb = inject(FormBuilder);
  private http = inject(HttpClient);

  // Reactive Signals
  backendStatus = signal<'Checking...' | 'Online' | 'Offline'>('Checking...');
  isProcessing = signal(false);

  // Mock Cart Data
  cartItems = [
    { id: 1, name: 'Premium Wireless Headphones', price: 250, color: 'Midnight Black', quantity: 1 },
    { id: 2, name: 'Ergonomic Mouse', price: 85, color: 'Matte Grey', quantity: 1 },
  ];

  // Computed Totals
  subtotal = computed(() => this.cartItems.reduce((acc, item) => acc + item.price * item.quantity, 0));
  shipping = computed(() => 15.00);
  total = computed(() => this.subtotal() + this.shipping());

  checkoutForm: FormGroup = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    address: ['', Validators.required],
    city: ['', Validators.required],
    zip: ['', Validators.required],
    cardNumber: ['', Validators.required],
    expiry: ['', Validators.required],
    cvc: ['', Validators.required],
  });

  ngOnInit() {
    this.checkHealth();
  }

  // Simulate Backend Health Check
    checkHealth() {
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

  onSubmit() {
    if (this.checkoutForm.valid) {
      this.isProcessing.set(true);

      // Simulate Order Submission
      setTimeout(() => {
        this.isProcessing.set(false);
        alert('Order placed successfully! (Simulation)');
        this.checkoutForm.reset();
      }, 2000);
    } else {
      this.checkoutForm.markAllAsTouched();
    }
  }

  isInvalid(field: string): boolean {
    const control = this.checkoutForm.get(field);
    return !!(control && control.invalid && (control.dirty || control.touched));
  }

  // Dynamic classes for the status indicator
  statusColorClass() {
    switch(this.backendStatus()) {
      case 'Online': return 'bg-green-500';
      case 'Offline': return 'bg-red-500';
      default: return 'bg-yellow-500';
    }
  }

  statusPingClass() {
     switch(this.backendStatus()) {
      case 'Online': return 'bg-green-400';
      case 'Offline': return 'bg-red-400';
      default: return 'bg-yellow-400';
    }
  }

  statusTextClass() {
    switch(this.backendStatus()) {
      case 'Online': return 'text-green-700';
      case 'Offline': return 'text-red-700';
      default: return 'text-yellow-700';
    }
  }
}