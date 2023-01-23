import { Component, Input, OnInit } from '@angular/core';
import { CartItem } from 'src/app/shared/interfaces/cart.interface';
import { Item } from 'src/app/shared/interfaces/item.interface';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-cart-item',
  templateUrl: './cart-item.component.html',
  styleUrls: ['./cart-item.component.scss'],
})
export class CartItemComponent implements OnInit {
  @Input() cartItem: CartItem;
  @Input() removeCartItem: (item: Item) => void;
  @Input() udpateCartItem: (item: Item, count: number) => void;

  quantity: number = 0;

  ngOnInit(): void {
    this.quantity = this.cartItem.count;
  }

  onRemoveClick(item: Item) {
    this.removeCartItem(item);
  }

  onAddClick(item: Item) {
    if (this.quantity < 1) {
      return;
    }
    this.quantity++;
    this.udpateCartItem(item, this.quantity);
  }

  onSubtractClick(item: Item) {
    if (this.quantity <= 1) {
      return;
    }
    this.quantity--;
    this.udpateCartItem(item, this.quantity);
  }

  onCountChangeThroughText(item: Item) {
    if (this.quantity < 1) {
      return;
    }
    this.udpateCartItem(item, this.quantity);
  }
}
