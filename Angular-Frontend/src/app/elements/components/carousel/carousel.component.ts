import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-carousel',
  templateUrl: './carousel.component.html',
  styleUrls: ['./carousel.component.scss'],
})
export class CarouselComponent {
  @Input() carouselItems: any;
  @Input() showText: boolean;

  active: string = 'carousel-item active';
  deactive: string = 'carousel-item';
  link =
    'http://api.letsdiskuss.com/resources/static/asset/uploads/1650505823637-aviv-rachmadian-7F7kEHj72MQ-unsplash-scaled.jpg';
}
