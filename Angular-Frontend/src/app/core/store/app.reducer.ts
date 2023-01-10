import { ActionReducerMap } from '@ngrx/store';
import { Auth } from 'src/app/shared/interfaces/auth.interface';
import { Cart } from 'src/app/shared/interfaces/cart.interface';
import { authReducer } from './auth/auth.reducer';
import { cartReducer } from './cart/cart.reducer';

export interface AppState {
  authState: Auth;
  cartState: Cart;
}

export const reducers: ActionReducerMap<AppState> = {
  authState: authReducer,
  cartState: cartReducer,
};
