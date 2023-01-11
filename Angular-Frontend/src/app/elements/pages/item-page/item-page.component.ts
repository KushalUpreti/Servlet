import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/services/auth.service';
import { CartService } from 'src/app/core/services/cart.service';

@Component({
  selector: 'app-item-page',
  templateUrl: './item-page.component.html',
  styleUrls: ['./item-page.component.scss'],
})
export class ItemPageComponent implements OnInit {
  constructor(
    private readonly http: HttpClient,
    private readonly router: Router,
    private readonly authService: AuthService,
    private readonly cartService: CartService
  ) {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  }

  item: any = null;

  carouselItems = [
    {
      imageURL: 'https://m.media-amazon.com/images/I/61Dw5Z8LzJL._SL1000_.jpg',
      title: "Women's Fashion",
      caption:
        'Pick outfits that fit the setting. Let your characters change outfits.',
    },
    {
      imageURL: 'https://m.media-amazon.com/images/I/61Dw5Z8LzJL._SL1000_.jpg',
      title: "Women's Fashion",
      caption:
        'Pick outfits that fit the setting. Let your characters change outfits.',
    },
    {
      imageURL: 'https://m.media-amazon.com/images/I/61Dw5Z8LzJL._SL1000_.jpg',
      title: "Women's Fashion",
      caption:
        'Pick outfits that fit the setting. Let your characters change outfits.',
    },
  ];
  showText: boolean = false;

  ngOnInit(): void {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const id = urlParams.get('id');

    if (!id) {
      return;
    }

    this.http
      .get<any>(`http://localhost:8080/guest/item/${id}`)
      .subscribe((item) => {
        this.item = item;
      });
  }

  addToCart(itemId: number) {
    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['/login']);
      return;
    }
    const userId = this.authService.getAuth().id;
    this.cartService.addItemToRemote(itemId, userId);
  }
}
