import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Auth } from 'src/app/shared/interfaces/auth.interface';
import { Cart } from 'src/app/shared/interfaces/cart.interface';
import { AuthService } from '../services/auth.service';
import { CartService } from '../services/cart.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  constructor(
    private readonly authService: AuthService,
    private readonly cartService: CartService,
    private readonly http: HttpClient,
    private readonly router: Router
  ) {}

  categories: any = [];

  ngOnInit(): void {
    if (this.authService.getAuth()) {
      this.http
        .get<any>(
          `http://localhost:8080/api/cart/getCartItemCount/${
            this.authService.getAuth().id
          }`
        )
        .subscribe((count) => {
          this.cartService.setCart(count);
        });
    }

    this.http
      .get<any>(`http://localhost:8080/api/guest/category/`)
      .subscribe((categories) => {
        this.categories = categories;
      });
  }

  getAuth(): Auth {
    return this.authService.getAuth();
  }

  getCount(): number {
    return this.cartService.getCart() ? this.cartService.getCart().count : 0;
  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
