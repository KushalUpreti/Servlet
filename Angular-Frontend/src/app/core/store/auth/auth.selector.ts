import { authFeatureKey } from './auth.reducer';
import { createFeatureSelector, createSelector } from '@ngrx/store';
import { Auth } from 'src/app/shared/interfaces/auth.interface';

export const selectAuthState = createFeatureSelector<Auth>(authFeatureKey);

export const selectAuth = createSelector(
  selectAuthState,
  (state: Auth) => state && state
);
