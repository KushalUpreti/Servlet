import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-cart-itemlist',
  templateUrl: './cart-itemlist.component.html',
  styleUrls: ['./cart-itemlist.component.scss'],
})
export class CartItemlistComponent {
  @Input() cartItems: any = [];
  @Input() removeCartItem: (cartItemId: number) => void;
}
