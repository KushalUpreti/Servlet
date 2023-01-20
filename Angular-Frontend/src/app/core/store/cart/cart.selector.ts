import { cartFeatureKey } from './cart.reducer';
import { createFeatureSelector, createSelector } from '@ngrx/store';
import { Cart } from 'src/app/shared/interfaces/cart.interface';

export const selectCartState = createFeatureSelector<Cart>(cartFeatureKey);

export const selectCart = createSelector(
  selectCartState,
  (state: Cart) => state && state.items
);
