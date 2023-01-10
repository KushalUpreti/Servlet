import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import * as CartActions from './cart.actions';

import { Router } from '@angular/router';
import { catchError, EMPTY, switchMap, TimeoutError } from 'rxjs';
import { StringResponse } from '../../../shared/interfaces/string-response.interface';

@Injectable()
export class CartEffects {
  constructor(
    private readonly actions$: Actions,
    private readonly http: HttpClient,
    private readonly router: Router
  ) {}

  postAddCartItem$ = createEffect(() =>
    this.actions$.pipe(
      ofType(CartActions.addItemToRemote),
      switchMap(({ itemId, quantity, userId }) => {
        return this.http
          .post<StringResponse>(
            `http://localhost:8080/api/cart/addCartItem/${itemId}/${userId}`,
            { quantity }
          )
          .pipe(
            switchMap((response) => {
              return [CartActions.addItemToCart({ count: 1 })];
            }),
            catchError((err: HttpErrorResponse | TimeoutError) => EMPTY)
          );
      })
    )
  );

  deleteCartItem$ = createEffect(() =>
    this.actions$.pipe(
      ofType(CartActions.removeItemFromRemote),
      switchMap(({ cartItemId }) => {
        return this.http
          .delete<number>(
            `http://localhost:8080/api/cart/deleteCartItem/${cartItemId}`
          )
          .pipe(
            switchMap((response) => {
              return [CartActions.removeItemFromCart({ count: response })];
            }),
            catchError((err: HttpErrorResponse | TimeoutError) => EMPTY)
          );
      })
    )
  );
}
