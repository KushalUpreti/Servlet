import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import * as CartActions from '../cart/cart.actions';
import * as AuthActions from './auth.actions';

import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { catchError, EMPTY, switchMap, TimeoutError } from 'rxjs';
import { Auth } from 'src/app/shared/interfaces/auth.interface';
import { AuthService } from '../../services/auth.service';

@Injectable()
export class AuthEffects {
  constructor(
    private readonly actions$: Actions,
    private readonly http: HttpClient,
    private readonly router: Router,
    private readonly authService: AuthService
  ) {}

  fetchAuthCredentials$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AuthActions.fetchAuthCredentials),
      switchMap((action) => {
        return this.http
          .post<Auth>(`http://localhost:8080/auth?type=login`, action)
          .pipe(
            switchMap((response) => {
              localStorage.setItem('__user-auth__', JSON.stringify(response));
              if (this.authService.redirectUrl) {
                this.router.navigate([this.authService.redirectUrl]);
                this.authService.redirectUrl = null;
              } else {
                this.router.navigate(['/']);
              }
              return [
                AuthActions.setAuthCredentials(response),
                CartActions.resetCart(),
              ];
            }),
            catchError((err: HttpErrorResponse | TimeoutError) => EMPTY)
          );
      })
    )
  );
}
