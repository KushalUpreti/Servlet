import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EffectsModule } from '@ngrx/effects';
import { LoginPageComponent } from 'src/app/elements/pages/login-page/login-page.component';
import { AuthEffects } from '../store/auth/auth.effects';
import { SharedModule } from './shared.module';

const routes: Routes = [{ path: '', component: LoginPageComponent }];

@NgModule({
  declarations: [LoginPageComponent],
  imports: [
    CommonModule,
    EffectsModule.forFeature([AuthEffects]),
    SharedModule,
    RouterModule.forChild(routes),
  ],
})
export class LoginPageModule {}
