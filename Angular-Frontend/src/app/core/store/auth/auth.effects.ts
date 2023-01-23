import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import * as AuthActions from './auth.actions';
import * as CartActions from '../cart/cart.actions';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { catchError, EMPTY, switchMap, TimeoutError } from 'rxjs';
import { Auth } from 'src/app/shared/interfaces/auth.interface';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';

@Injectable()
export class AuthEffects {
  constructor(
    private readonly actions$: Actions,
    private readonly http: HttpClient,
    private readonly router: Router,
    private readonly store: Store
  ) {}

  fetchAuthCredentials$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AuthActions.fetchAuthCredentials),
      switchMap((action) => {
        return this.http
          .post<Auth>(`http://localhost:8080/auth?type=login`, action)
          .pipe(
            switchMap((response) => {
              this.router.navigate(['/']);
              localStorage.setItem('__user-auth__', JSON.stringify(response));
              this.store.dispatch(CartActions.resetCart());
              return [AuthActions.setAuthCredentials(response)];
            }),
            catchError((err: HttpErrorResponse | TimeoutError) => EMPTY)
          );
      })
    )
  );
}
