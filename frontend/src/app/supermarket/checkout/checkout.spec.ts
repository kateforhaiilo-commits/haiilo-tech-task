import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckoutComponent } from './checkout';
import { provideZonelessChangeDetection } from '@angular/core';
import { vi } from 'vitest';
import { CheckoutStore } from '../../store/checkout.store';

class MockCheckoutStore {
  items = vi.fn();
  total = vi.fn();
}

describe('CheckoutComponent', () => {
  let component: CheckoutComponent;
  let fixture: ComponentFixture<CheckoutComponent>;
  let mockStore: MockCheckoutStore;

  beforeEach(async () => {
    mockStore = new MockCheckoutStore();

    await TestBed.configureTestingModule({
      imports: [CheckoutComponent],
      providers: [
        provideZonelessChangeDetection(),
        { provide: CheckoutStore, useValue: mockStore }

      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CheckoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
