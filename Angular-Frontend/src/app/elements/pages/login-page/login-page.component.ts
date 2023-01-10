import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import { fetchAuthCredentials } from 'src/app/core/store/auth/auth.actions';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss'],
})
export class LoginPageComponent {
  constructor(private readonly store: Store) {}

  email = new FormControl('', [Validators.required, Validators.email]);
  password = new FormControl('', [
    Validators.required,
    Validators.maxLength(15),
  ]);

  login() {
    let emailText = this.email.value;
    let passwordText = this.password.value;
    this.store.dispatch(
      fetchAuthCredentials({ email: emailText, password: passwordText })
    );
  }

  getEmailErrorMessage() {
    if (this.email.hasError('required')) {
      return 'You must enter a value';
    }
    return this.email.hasError('email') ? 'Not a valid email' : '';
  }
  getPasswordErrorMessage() {
    if (this.password.hasError('required')) {
      return 'You must enter a value';
    }
    return this.password.hasError('maxlength') ? 'Invalid password length' : '';
  }
}
