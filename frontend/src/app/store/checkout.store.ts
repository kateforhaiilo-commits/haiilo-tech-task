import { inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { signalStore, withState, withComputed, withMethods, patchState } from '@ngrx/signals';
import { catchError, of, tap } from 'rxjs';

export interface Item {
  id: number;
  name: string;
  price: number;
}

export const CheckoutStore = signalStore(
  { providedIn: 'root' },
  withState({
    items: [] as Item[],
    total: 0,
    status: 'idle' as 'idle' | 'loading' | 'success' | 'error',
  }),
  withComputed(() => ({
  })),
  withMethods((store) => {
    const http = inject(HttpClient);
    return {
      loadItems: () => {
        patchState(store, { status: 'loading' });
        http.get<Item[]>('http://localhost:8080/api/items').pipe(
          tap((items: any) => {
            console.log('Fetched items:', items);
            patchState(store, { items, status: 'success' });
          }),
          catchError(() => {
            patchState(store, { status: 'error' });
            return of([]);
          })
        ).subscribe();
      },
    };
  })
);
