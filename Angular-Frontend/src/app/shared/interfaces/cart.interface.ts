import { Item } from './item.interface';

export interface Cart {
  items: CartItem[];
}

export interface CartItem {
  item: Item;
  count: number;
}
