import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminItemPageComponent } from 'src/app/elements/pages/admin-item-page/admin-item-page.component';
import { SharedModule } from './shared.module';
import { MatSelectModule } from '@angular/material/select';
import { CommonModule } from '@angular/common';
import { NgbCarouselConfig } from '@ng-bootstrap/ng-bootstrap';
import { ItemFormComponent } from 'src/app/elements/components/item-form/item-form.component';

const routes: Routes = [{ path: '', component: AdminItemPageComponent }];

@NgModule({
  declarations: [AdminItemPageComponent, ItemFormComponent],
  imports: [
    SharedModule,
    CommonModule,
    RouterModule.forChild(routes),
    MatSelectModule,
  ],
  providers: [NgbCarouselConfig],
})
export class AdditemPageModule {}
