import { createAction, props } from '@ngrx/store';
import { Cart, CartItem } from '../../../shared/interfaces/cart.interface';

export const addItemToCart = createAction(
  '[Cart Component] Add Cart Item',
  props<Cart>()
);

export const addItemToRemote = createAction(
  '[Cart Component] Add Cart Item Remote',
  props<CartItem>()
);

export const removeItemFromCart = createAction(
  '[Cart Component] Remove Cart Item',
  props<Cart>()
);

export const resetCart = createAction(
  '[Cart Component] Reset Cart Item',
  props<Cart>()
);

export const removeItemFromRemote = createAction(
  '[Cart Component] Remove Cart Item Remote',
  props<{ cartItemId: number }>()
);
