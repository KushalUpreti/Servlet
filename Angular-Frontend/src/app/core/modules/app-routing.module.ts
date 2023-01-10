import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../guards/auth.guard';
import { RoleGuard } from '../guards/role.guard';
import { SessionGuard } from '../guards/session.guard';

const routes: Routes = [
  {
    path: '',
    loadChildren: () =>
      import('./home-page.module').then((m) => m.HomePageModule),
  },
  {
    path: 'login',
    loadChildren: () =>
      import('./login-page.module').then((m) => m.LoginPageModule),
    canActivate: [SessionGuard],
  },
  {
    path: 'cart',
    loadChildren: () =>
      import('./cart-page.module').then((m) => m.CartPageModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'add-item',
    loadChildren: () =>
      import('./additem-page.module').then((m) => m.AdditemPageModule),
    canActivate: [AuthGuard, RoleGuard],
  },
  {
    path: 'add-category',
    loadChildren: () =>
      import('./addcategory-page.module').then((m) => m.AddcategoryPageModule),
    canActivate: [AuthGuard, RoleGuard],
  },
  {
    path: 'item',
    loadChildren: () =>
      import('./item-page.module').then((m) => m.ItemPageModule),
    // canActivate: [AuthGuard, RoleGuard],
  },
  {
    path: '**',
    loadChildren: () =>
      import('./notfound-page.module').then((m) => m.NotfoundPageModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
