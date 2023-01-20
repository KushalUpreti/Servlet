import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { AuthService } from 'src/app/core/services/auth.service';
import { Item } from 'src/app/shared/interfaces/item.interface';
import * as CartActions from '../../../core/store/cart/cart.actions';

@Component({
  selector: 'app-item-card',
  templateUrl: './item-card.component.html',
  styleUrls: ['./item-card.component.scss'],
})
export class ItemCardComponent {
  @Input() item: any;

  constructor(
    private readonly authService: AuthService,
    private readonly store: Store,
    private readonly router: Router
  ) {}

  addToCart(item: Item) {
    this.store.dispatch(CartActions.addItemToCart(item));
  }
}
