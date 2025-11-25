import { inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { signalStore, withState, withMethods, patchState } from '@ngrx/signals';
import { catchError, of, tap } from 'rxjs';

export interface Offer {
  price: number;
  quantity: number;
}

export interface Item {
  id: number;
  name: string;
  price: number;
  offer: null | Offer;
  quantity: number;
}

export interface StateInterface {
  items: Item[];
  total: number;
  status: 'idle' | 'loading' | 'success' | 'error';
}

export const CheckoutStore = signalStore(
  { providedIn: 'root' },
  withState({
    items: [] as Item[],
    total: 0,
    status: 'idle' as 'idle' | 'loading' | 'success' | 'error',
  }),
  withMethods((store) => {
    const http = inject(HttpClient);
    return {
      loadItems: () => {
        patchState(store, { status: 'loading' });
        http
          .get<Item[]>('http://localhost:8080/api/items')
          .pipe(
            tap((items: Item[]) => {
              patchState(store, { items, status: 'success' });
            }),
            catchError(() => {
              patchState(store, { status: 'error' });
              return of([]);
            }),
          )
          .subscribe();
      },
      updateItemQuantity: (itemName: string, itemQuantity: number) => {
        // TODO add loading indicator to template
        patchState(store, { status: 'loading' });

        const updatedItems = store
          .items()
          .map((item) => (item.name === itemName ? { ...item, quantity: itemQuantity } : item));

        patchState(store, { items: updatedItems });

        // TODO instead of sending everything only send necessary data
        http
          .post<{ total: number }>('http://localhost:8080/api/checkout', { items: store.items() })
          .pipe(
            tap((response) => {
              patchState(store, { total: response.total, status: 'success' });
            }),
            catchError(() => {
              patchState(store, { status: 'error' });
              return of(null);
            }),
          )
          .subscribe();
      },
    };
  }),
);
