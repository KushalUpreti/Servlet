import { Injectable } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { Subject, takeUntil } from 'rxjs';
import { Cart, CartItem } from 'src/app/shared/interfaces/cart.interface';
import * as CartActions from '../store/cart/cart.actions';
import { selectCart } from '../store/cart/cart.selector';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private unsubscribe = new Subject<void>();
  cartItems: CartItem[] = [];

  constructor(private readonly store: Store) {
    const cart = this.getSavedState('__user-cart__');
    if (cart) {
      this.store.dispatch(CartActions.loadCart(cart));
    }

    this.store
      .pipe(select(selectCart), takeUntil(this.unsubscribe))
      .subscribe((items) => {
        this.cartItems = items;
      });
  }

  getSavedState(localStorageKey: string): Cart {
    return JSON.parse(localStorage.getItem(localStorageKey));
  }

  getTotalPrice(): number {
    let price = 0;
    this.cartItems.forEach((cartItem) => {
      price += cartItem.count * cartItem.item.price;
    });
    return price;
  }

  getTotalQuantity(): number {
    let count = 0;
    this.cartItems.forEach((cartItem) => {
      count += cartItem.count;
    });
    return count;
  }

  getCartItems(): CartItem[] {
    return this.cartItems;
  }
}
