import { Injectable } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { Cart } from 'src/app/shared/interfaces/cart.interface';
import { selectCart } from '../store/cart/cart.selector';
import * as CartActions from '../store/cart/cart.actions';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  cart: Cart = { count: 0 };

  constructor(private readonly store: Store) {
    store.pipe(select(selectCart)).subscribe((count) => {
      if (count) {
        this.cart.count = count;
      }
    });
  }

  getCart(): Cart {
    return this.cart;
  }

  setCart(count: number) {
    this.store.dispatch(CartActions.addItemToCart({ count }));
  }

  addItemToRemote(itemId: number, userId: number) {
    this.store.dispatch(
      CartActions.addItemToRemote({ itemId, userId, quantity: 1 })
    );
  }
}
