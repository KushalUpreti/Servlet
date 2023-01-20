import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import { Cart } from 'src/app/shared/interfaces/cart.interface';
import * as CartActions from '../store/cart/cart.actions';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  constructor(private readonly store: Store) {
    const cart = this.getSavedState('__user-cart__');

    if (cart) {
      this.store.dispatch(CartActions.loadCart(cart));
    }
  }

  getSavedState(localStorageKey: string): Cart {
    return JSON.parse(localStorage.getItem(localStorageKey));
  }
}
