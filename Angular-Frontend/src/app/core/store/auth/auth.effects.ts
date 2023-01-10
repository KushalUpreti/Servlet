import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import * as AuthActions from './auth.actions';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { catchError, EMPTY, switchMap, TimeoutError } from 'rxjs';
import { Auth } from 'src/app/shared/interfaces/auth.interface';
import { Router } from '@angular/router';

@Injectable()
export class AuthEffects {
  constructor(
    private readonly actions$: Actions,
    private readonly http: HttpClient,
    private readonly router: Router
  ) {}

  fetchAuthCredentials$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AuthActions.fetchAuthCredentials),
      switchMap((action) => {
        return this.http
          .post<Auth>(`http://localhost:8080/api/auth/login`, action)
          .pipe(
            switchMap((response) => {
              this.router.navigate(['/']);
              localStorage.setItem('userAuth', JSON.stringify(response));
              return [AuthActions.setAuthCredentials(response)];
            }),
            catchError((err: HttpErrorResponse | TimeoutError) => EMPTY)
          );
      })
    )
  );
}
