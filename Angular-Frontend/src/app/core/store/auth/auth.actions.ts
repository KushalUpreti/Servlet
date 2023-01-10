import { createAction, props } from '@ngrx/store';
import { Auth } from '../../../shared/interfaces/auth.interface';

export const deleteAuthCredentials = createAction(
  '[Auth Component] Delete Auth'
);

export const setAuthCredentials = createAction(
  '[Auth Component] Set Auth',
  props<Auth>()
);

export const fetchAuthCredentials = createAction(
  '[Auth Component] Fetch Auth',
  props<{ email: string; password: string }>()
);
