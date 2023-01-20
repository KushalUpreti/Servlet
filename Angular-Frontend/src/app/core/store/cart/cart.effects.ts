import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Actions } from '@ngrx/effects';

import { Router } from '@angular/router';

@Injectable()
export class CartEffects {
  constructor(
    private readonly actions$: Actions,
    private readonly http: HttpClient,
    private readonly router: Router
  ) {}

  // postAddCartItem$ = createEffect(() =>
  //   this.actions$.pipe(
  //     ofType(CartActions.addItemToRemote),
  //     switchMap(({ itemId, quantity, userId }) => {
  //       return this.http
  //         .post<StringResponse>(
  //           `http://localhost:8080/api/cart/addCartItem/${itemId}/${userId}`,
  //           { quantity }
  //         )
  //         .pipe(
  //           switchMap((response) => {
  //             return [CartActions.addItemToCart({ count: 1 })];
  //           }),
  //           catchError((err: HttpErrorResponse | TimeoutError) => EMPTY)
  //         );
  //     })
  //   )
  // );

  // deleteCartItem$ = createEffect(() =>
  //   this.actions$.pipe(
  //     ofType(CartActions.removeItemFromRemote),
  //     switchMap(({ cartItemId }) => {
  //       return this.http
  //         .delete<number>(
  //           `http://localhost:8080/api/cart/deleteCartItem/${cartItemId}`
  //         )
  //         .pipe(
  //           switchMap((response) => {
  //             return [CartActions.removeItemFromCart({ count: response })];
  //           }),
  //           catchError((err: HttpErrorResponse | TimeoutError) => EMPTY)
  //         );
  //     })
  //   )
  // );
}
