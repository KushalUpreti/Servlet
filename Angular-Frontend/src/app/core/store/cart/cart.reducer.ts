import { createReducer, on } from '@ngrx/store';
import { Cart } from 'src/app/shared/interfaces/cart.interface';
import * as CartActions from './cart.actions';
import cloneDeep from 'lodash/cloneDeep';

export const cartInitialState: Cart = { items: [] };

export const cartFeatureKey = 'cartState';

function setSavedState(state: Cart, localStorageKey: string) {
  localStorage.setItem(localStorageKey, JSON.stringify(state));
}

export const cartReducer = createReducer(
  cartInitialState,
  on(CartActions.addItemToCart, (state, payload) => {
    const cartCopy: Cart = cloneDeep(state);
    const item = cartCopy.items.find(
      (cartItem) => cartItem.item.id === payload.item.id
    );
    if (!item) {
      cartCopy.items.push({ item: payload.item, count: 1 });
    } else {
      item.count += payload.count;
    }
    setSavedState(cartCopy, '__user-cart__');
    return cartCopy;
  }),
  on(CartActions.loadCart, (state, payload) => {
    const cart: Cart = payload;
    return cart;
  }),
  on(CartActions.updateCartItem, (state, payload) => {
    const cartCopy: Cart = cloneDeep(state);
    const item = cartCopy.items.find(
      (cartItem) => cartItem.item.id === payload.item.id
    );
    item.count = payload.count;
    setSavedState(cartCopy, '__user-cart__');
    return cartCopy;
  }),
  on(CartActions.removeItemFromCart, (state, payload) => {
    const cartCopy: Cart = cloneDeep(state);

    const item = cartCopy.items.find((cartItem) => {
      return cartItem.item.id === payload.id;
    });
    // if (item.count === 1) {
    cartCopy.items = cartCopy.items.filter(
      (cartItem) => cartItem.item.id !== payload.id
    );
    // } else {
    //   item.count--;
    // }
    setSavedState(cartCopy, '__user-cart__');
    return cartCopy;
  }),
  on(CartActions.resetCart, (state, payload) => {
    setSavedState(cartInitialState, '__user-cart__');
    return cartInitialState;
  })
);
