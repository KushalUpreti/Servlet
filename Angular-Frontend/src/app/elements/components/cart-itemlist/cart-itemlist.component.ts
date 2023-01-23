import { Component, Input } from '@angular/core';
import { Item } from 'src/app/shared/interfaces/item.interface';

@Component({
  selector: 'app-cart-itemlist',
  templateUrl: './cart-itemlist.component.html',
  styleUrls: ['./cart-itemlist.component.scss'],
})
export class CartItemlistComponent {
  @Input() cartItems: any = [];
  @Input() removeCartItem: (item: Item) => void;
  @Input() udpateCartItem: (item: Item, count: number) => void;
}
