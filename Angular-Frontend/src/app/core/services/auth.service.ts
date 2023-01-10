import { Injectable } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { Auth } from 'src/app/shared/interfaces/auth.interface';
import * as AuthActions from '../store/auth/auth.actions';
import { selectAuth } from '../store/auth/auth.selector';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  auth: Auth;

  constructor(private readonly store: Store) {
    store.pipe(select(selectAuth)).subscribe((auth) => {
      if (auth) {
        this.auth = auth;
      }
    });
    const userAuth = JSON.parse(localStorage.getItem('userAuth'));
    if (userAuth) {
      this.store.dispatch(AuthActions.setAuthCredentials(userAuth));
    }
  }

  isAuthenticated(): boolean {
    return !!this.auth && !!this.auth.token;
  }

  getAuth(): Auth {
    return this.auth;
  }

  logout(): void {
    this.store.dispatch(AuthActions.deleteAuthCredentials());
    this.auth = null;
    localStorage.removeItem('userAuth');
  }
}
