import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdditemPageComponent } from 'src/app/elements/pages/additem-page/additem-page.component';
import { SharedModule } from './shared.module';
import { MatSelectModule } from '@angular/material/select';
import { CommonModule } from '@angular/common';
import { NgbCarouselConfig } from '@ng-bootstrap/ng-bootstrap';

const routes: Routes = [{ path: '', component: AdditemPageComponent }];

@NgModule({
  declarations: [AdditemPageComponent],
  imports: [
    SharedModule,
    CommonModule,
    RouterModule.forChild(routes),
    MatSelectModule,
  ],
  providers: [NgbCarouselConfig],
})
export class AdditemPageModule {}
