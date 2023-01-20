import { Component, Input } from '@angular/core';
import { Item } from 'src/app/shared/interfaces/item.interface';

@Component({
  selector: 'app-cart-item',
  templateUrl: './cart-item.component.html',
  styleUrls: ['./cart-item.component.scss'],
})
export class CartItemComponent {
  @Input() cartItem: any;
  @Input() removeCartItem: (item: Item) => void;

  onRemoveClick(item: Item) {
    this.removeCartItem(item);
  }
}
