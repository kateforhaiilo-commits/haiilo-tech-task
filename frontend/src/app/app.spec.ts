import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, provideZonelessChangeDetection } from '@angular/core';
import { vi } from 'vitest';
import { App } from './app';
import { CheckoutStore } from './store/checkout.store';

@Component({ selector: 'header', template: '', standalone: true })
class MockHeaderComponent {}

@Component({ selector: 'items', template: '', standalone: true })
class MockItemsComponent {}

@Component({ selector: 'checkout', template: '', standalone: true })
class MockCheckoutComponent {}

class MockCheckoutStore {
  loadItems = vi.fn();
}

describe('App', () => {
  let component: App;
  let fixture: ComponentFixture<App>;
  let mockStore: MockCheckoutStore;

  beforeEach(async () => {
    mockStore = new MockCheckoutStore();

    await TestBed.configureTestingModule({
      imports: [App],
      providers: [
        provideZonelessChangeDetection(),
        { provide: CheckoutStore, useValue: mockStore }
      ],
    })
    .overrideComponent(App, {
      set: { imports: [MockHeaderComponent, MockItemsComponent, MockCheckoutComponent] },
    }).compileComponents();

    fixture = TestBed.createComponent(App);
    component = fixture.componentInstance;
  });

  it('should create the app', () => {
    expect(component).toBeTruthy();
  });

  it('should call loadItems on the store during initialization', () => {
    fixture.detectChanges();
    expect(mockStore.loadItems).toHaveBeenCalled();
  });
});
