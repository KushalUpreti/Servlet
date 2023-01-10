import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-cart-item',
  templateUrl: './cart-item.component.html',
  styleUrls: ['./cart-item.component.scss'],
})
export class CartItemComponent {
  @Input() cartItem: any;
  @Input() removeCartItem: (cartItemId: number) => void;

  onRemoveClick(cartItemId: number) {
    this.removeCartItem(cartItemId);
  }
}
