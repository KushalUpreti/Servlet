import { cartFeatureKey, CartState } from './cart.reducer';
import { createFeatureSelector, createSelector } from '@ngrx/store';

export const selectCartState = createFeatureSelector<CartState>(cartFeatureKey);

export const selectCart = createSelector(
  selectCartState,
  (state: CartState) => state && state.count
);
