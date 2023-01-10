import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.scss'],
})
export class OrderDetailComponent {
  @Input() cartItems: any;
  @Input() getTotalQuantity: any;
  @Input() getTotalPrice: any;
  @Input() accept: any;
}
