import { createReducer, on } from '@ngrx/store';
import { Cart } from 'src/app/shared/interfaces/cart.interface';
import * as CartActions from './cart.actions';

export interface CartState {
  count: number;
}

export const cartInitialState: Cart = { count: 0 };

export const cartFeatureKey = 'cartState';

export const cartReducer = createReducer(
  cartInitialState,
  on(CartActions.addItemToCart, (state, payload) => {
    let prevCount = state.count;
    const newCount = prevCount + payload.count;
    return { ...state, count: newCount };
  }),
  on(CartActions.removeItemFromCart, (state, payload) => {
    let prevCount = state.count;
    const newCount: number = prevCount - payload.count;
    return { ...state, count: newCount };
  }),
  on(CartActions.resetCart, (state, payload) => {
    return { ...state, count: 0 };
  })
);
