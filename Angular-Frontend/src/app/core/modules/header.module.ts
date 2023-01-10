import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../header/header.component';
import { MaterialModule } from './material.module';
import { UsernamePipe } from 'src/app/shared/pipes/username.pipe';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [HeaderComponent, UsernamePipe],
  imports: [CommonModule, MaterialModule, RouterModule, HttpClientModule],
  exports: [HeaderComponent],
})
export class HeaderModule {}
