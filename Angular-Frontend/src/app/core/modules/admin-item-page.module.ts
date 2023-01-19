import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminItemPageComponent } from 'src/app/elements/pages/admin-item-page/admin-item-page.component';
import { SharedModule } from './shared.module';
import { MatSelectModule } from '@angular/material/select';
import { CommonModule } from '@angular/common';
import { NgbCarouselConfig } from '@ng-bootstrap/ng-bootstrap';
import { ItemFormComponent } from 'src/app/elements/components/item-form/item-form.component';
import { ItemAddWrapperComponent } from 'src/app/elements/wrappers/item-add-wrapper/item-add-wrapper.component';
import { ItemEditWrapperComponent } from 'src/app/elements/wrappers/item-edit-wrapper/item-edit-wrapper.component';

const routes: Routes = [
  {
    path: '',
    component: AdminItemPageComponent,
    children: [
      { path: '', redirectTo: 'add', pathMatch: 'full' },
      { path: 'add', component: ItemAddWrapperComponent },
      { path: 'edit', component: ItemEditWrapperComponent },
    ],
  },
];

@NgModule({
  declarations: [
    AdminItemPageComponent,
    ItemFormComponent,
    ItemAddWrapperComponent,
    ItemEditWrapperComponent,
  ],
  imports: [
    SharedModule,
    CommonModule,
    RouterModule.forChild(routes),
    MatSelectModule,
  ],
  providers: [NgbCarouselConfig],
})
export class AdditemPageModule {}
