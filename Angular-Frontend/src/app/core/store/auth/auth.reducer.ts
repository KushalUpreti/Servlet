import { createReducer, on } from '@ngrx/store';
import { Auth } from 'src/app/shared/interfaces/auth.interface';
import * as AuthActions from './auth.actions';

export const authInitialState: Auth = {
  id: null,
  token: null,
  email: null,
  role: null,
};

export const authFeatureKey = 'authState';

export const authReducer = createReducer(
  authInitialState,
  on(AuthActions.setAuthCredentials, (state, payload) => {
    const auth = payload;
    return { ...state, ...auth };
  }),
  on(AuthActions.deleteAuthCredentials, (state, payload) => {
    return { ...state, ...authInitialState };
  })
);
