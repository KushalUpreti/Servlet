import { createAction, props } from '@ngrx/store';
import { Item } from 'src/app/shared/interfaces/item.interface';
import { Cart } from '../../../shared/interfaces/cart.interface';

export const loadCart = createAction(
  '[Cart Component] Load Cart',
  props<Cart>()
);

export const addItemToCart = createAction(
  '[Cart Component] Add Cart Item',
  props<Item>()
);

// export const addItemToRemote = createAction(
//   '[Cart Component] Add Cart Item Remote',
//   props<CartItem>()
// );

// export const removeItemFromCart = createAction(
//   '[Cart Component] Remove Cart Item',
//   props<Cart>()
// );

// export const removeItemFromRemote = createAction(
//   '[Cart Component] Remove Cart Item Remote',
//   props<{ cartItemId: number }>()
// );

// export const resetCart = createAction(
//   '[Cart Component] Reset Cart Item',
//   props<Cart>()
// );
