import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { provideZonelessChangeDetection, signal, WritableSignal } from '@angular/core';

import { ItemsComponent } from './items';
import { CheckoutStore, Item } from '../../store/checkout.store';
import { vi } from 'vitest';

// Mock implementation for the CheckoutStore
class MockCheckoutStore {
  items: WritableSignal<Item[]> = signal([]);
  updateItemQuantity = vi.fn();
}

describe('ItemsComponent', () => {
  let component: ItemsComponent;
  let fixture: ComponentFixture<ItemsComponent>;
  let mockStore: MockCheckoutStore;

  const mockItems: Item[] = [
    { id: 1, name: 'Apple', price: 30, offer: null, quantity: 2 },
    { id: 2, name: 'Banana', price: 50, offer: null, quantity: 0 },
  ];

  beforeEach(async () => {
    mockStore = new MockCheckoutStore();

    await TestBed.configureTestingModule({
      imports: [ItemsComponent, ReactiveFormsModule],
      providers: [
        provideZonelessChangeDetection(),
        { provide: CheckoutStore, useValue: mockStore },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ItemsComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should create form controls when items are loaded into the store', () => {
    mockStore.items.set(mockItems);
    fixture.detectChanges();

    expect(component.itemsForm.get('Apple')).not.toBeNull();
    expect(component.itemsForm.get('Banana')).not.toBeNull();
    expect(component.itemsForm.get('Apple')?.value).toBe(2);
    expect(component.itemsForm.get('Banana')?.value).toBe(0);
  });

  it('should call updateItemQuantity when a form control value changes', () => {
    mockStore.items.set(mockItems);
    fixture.detectChanges();

    const appleControl = component.itemsForm.get('Apple');
    expect(appleControl).not.toBeNull();

    appleControl?.setValue(5);
    fixture.detectChanges();

    expect(mockStore.updateItemQuantity).toHaveBeenCalledWith('Apple', 5);
  });

  it('should call updateItemQuantity with 0 if form control becomes invalid', () => {
    mockStore.items.set(mockItems);
    fixture.detectChanges();
    const appleControl = component.itemsForm.get('Apple');

    appleControl?.setValue(-1);
    fixture.detectChanges();

    expect(mockStore.updateItemQuantity).toHaveBeenCalledWith('Apple', 0);
  });

  it('should increment quantity via button click', () => {
    mockStore.items.set(mockItems);
    fixture.detectChanges();

    const appleControl = component.itemsForm.get('Apple');
    const initialValue = appleControl?.value;

    component.incrementQuantity('Apple');

    expect(appleControl?.value).toBe(initialValue + 1);
  });

  it('should not increment quantity beyond 999', () => {
    mockStore.items.set(mockItems);
    fixture.detectChanges();
    const appleControl = component.itemsForm.get('Apple');
    appleControl?.setValue(999);

    component.incrementQuantity('Apple');

    expect(appleControl?.value).toBe(999);
  });

  it('should decrement quantity via button click', () => {
    mockStore.items.set(mockItems);
    fixture.detectChanges();
    const appleControl = component.itemsForm.get('Apple');
    const initialValue = appleControl?.value;

    component.decrementQuantity('Apple');

    expect(appleControl?.value).toBe(initialValue - 1);
  });

  it('should not decrement quantity below 0', () => {
    mockStore.items.set(mockItems);
    fixture.detectChanges();
    const bananaControl = component.itemsForm.get('Banana');

    component.decrementQuantity('Banana');

    expect(bananaControl?.value).toBe(0);
  });
});
