import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { AuthService } from 'src/app/core/services/auth.service';
import { CartService } from 'src/app/core/services/cart.service';
import { CartItem } from 'src/app/shared/interfaces/cart.interface';
import { Item } from 'src/app/shared/interfaces/item.interface';
import * as CartActions from '../../../core/store/cart/cart.actions';

@Component({
  selector: 'app-cart-page',
  templateUrl: './cart-page.component.html',
  styleUrls: ['./cart-page.component.scss'],
})
export class CartPageComponent {
  constructor(
    private readonly http: HttpClient,
    private readonly authService: AuthService,
    private readonly cartService: CartService,
    private readonly store: Store,
    private readonly route: Router
  ) {}

  showModal: boolean = false;

  getCartItems() {
    return this.cartService.getCartItems();
  }

  removeCartItem = (item: Item) => {
    this.store.dispatch(CartActions.removeItemFromCart(item));
  };

  getTotalQuantity(): number {
    return this.cartService.getTotalQuantity();
  }

  getTotalPrice(): number {
    return this.cartService.getTotalPrice();
  }

  accept = () => {
    // this.http
    //   .post<any>(
    //     `http://localhost:8080/api/order/placeOrder/${
    //       this.authService.getAuth().id
    //     }`,
    //     {}
    //   )
    //   .subscribe(() => {
    //     // this.store.dispatch(resetCart({ count: 0 }));
    //     this.route.navigate(['/']);
    //   });
  };

  placeOrder = () => {
    // this.showModal = true;
  };
}
