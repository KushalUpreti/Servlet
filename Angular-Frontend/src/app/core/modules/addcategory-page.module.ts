import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from './shared.module';
import { AddCategoryPageComponent } from 'src/app/elements/pages/addcategory-page/addcategory-page.component';

const routes: Routes = [{ path: '', component: AddCategoryPageComponent }];

@NgModule({
  declarations: [AddCategoryPageComponent],
  imports: [SharedModule, RouterModule.forChild(routes)],
})
export class AddcategoryPageModule {}
