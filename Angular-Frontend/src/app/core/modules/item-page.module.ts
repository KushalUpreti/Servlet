import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule, Routes } from '@angular/router';
import { SharedModule } from './shared.module';
import { ItemPageComponent } from '../../elements/pages/item-page/item-page.component';
import { HttpClientModule } from '@angular/common/http';

const routes: Routes = [{ path: '', component: ItemPageComponent }];

@NgModule({
  declarations: [ItemPageComponent],
  imports: [CommonModule, RouterModule.forChild(routes), SharedModule],
  providers: [],
})
export class ItemPageModule {}
