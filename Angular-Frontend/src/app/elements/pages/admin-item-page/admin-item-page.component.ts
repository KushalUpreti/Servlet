import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
declare var bootstrap: any;

@Component({
  selector: 'app-additem-page',
  templateUrl: './admin-item-page.component.html',
  styleUrls: ['./admin-item-page.component.scss'],
})
export class AdminItemPageComponent implements OnInit {
  constructor(
    private readonly http: HttpClient,
    private readonly router: Router
  ) {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  }

  items = [];
  message: string = '';

  ngOnInit(): void {
    this.http
      .get<any>(`http://localhost:8080/admin/item`)
      .subscribe((items) => {
        this.items = items;
      });
  }

  changeModeToEdit(itemId: number) {
    this.router.navigate(['/admin-item', 'edit'], {
      queryParams: { item: itemId },
    });
  }

  changeModeToAdd() {
    this.router.navigate(['/admin-item', 'add']);
  }

  onAdd(item: any) {
    this.items.push(item);
    this.loadToast();
  }

  deleteItem(itemId: number) {
    this.http
      .delete<any>(`http://localhost:8080/admin/item/${itemId}`)
      .subscribe((res) => {
        this.message = 'Item deleted';
        this.loadToast();
        this.items = this.items.filter((item) => item.id !== itemId);
      });
  }

  loadToast(): void {
    const toastLiveExample = document.getElementById('liveToast');
    const toast = new bootstrap.Toast(toastLiveExample);
    toast.show();
  }
}
