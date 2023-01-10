import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { EffectsModule } from '@ngrx/effects';
import { CarouselComponent } from 'src/app/elements/components/carousel/carousel.component';
import { CartItemComponent } from 'src/app/elements/components/cart-item/cart-item.component';
import { CartItemlistComponent } from 'src/app/elements/components/cart-itemlist/cart-itemlist.component';
import { ItemCardComponent } from 'src/app/elements/components/item-card/item-card.component';
import { ItemsListComponent } from 'src/app/elements/components/items-list/items-list.component';
import { ToastComponent } from 'src/app/elements/components/toast/toast.component';
import { CartEffects } from '../store/cart/cart.effects';
import { MaterialModule } from './material.module';

@NgModule({
  declarations: [
    ItemCardComponent,
    ItemsListComponent,
    ToastComponent,
    CarouselComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule,
    EffectsModule.forFeature([CartEffects]),
  ],
  exports: [
    ItemCardComponent,
    ItemsListComponent,
    ReactiveFormsModule,
    CommonModule,
    MaterialModule,
    RouterModule,
    HttpClientModule,
    CommonModule,
    ToastComponent,
    CarouselComponent,
  ],
  providers: [],
})
export class SharedModule {}
