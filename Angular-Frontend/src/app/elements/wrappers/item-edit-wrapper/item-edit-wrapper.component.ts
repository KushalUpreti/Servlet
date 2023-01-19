import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
declare var bootstrap: any;

@Component({
  selector: 'app-item-edit-wrapper',
  templateUrl: './item-edit-wrapper.component.html',
  styleUrls: ['./item-edit-wrapper.component.scss'],
})
export class ItemEditWrapperComponent implements OnInit {
  constructor(private readonly http: HttpClient) {}
  formType: string = 'Edit';
  categories: [] = [];
  item: any = null;
  message: string = 'Item Added';

  ngOnInit(): void {
    this.getItemInfo();
    this.http
      .get<any>(`http://localhost:8080/guest/category?type=category`)
      .subscribe((categories) => {
        this.categories = categories;
      });
  }

  editItem(formData: FormData) {
    alert('Edited Item');
  }

  getItemInfo() {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const id = urlParams.get('item');

    if (!id) {
      return;
    }

    this.http
      .get<any>(`http://localhost:8080/guest/item/${id}`)
      .subscribe((item) => {
        this.item = item;
      });
  }

  deleteImage(imageId: number) {
    this.http
      .delete<any>(`http://localhost:8080/admin/image/${imageId}`)
      .subscribe(() => {
        this.item.images = this.item.images.filter(
          (image) => image.id !== imageId
        );
      });
  }
}
