import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from 'src/app/elements/pages/home-page/home-page.component';
import { SharedModule } from './shared.module';
import { NgbCarouselModule } from '@ng-bootstrap/ng-bootstrap';

const routes: Routes = [{ path: '', component: HomePageComponent }];

@NgModule({
  declarations: [HomePageComponent],
  imports: [SharedModule, RouterModule.forChild(routes), NgbCarouselModule],
})
export class HomePageModule {}
