import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
declare var bootstrap: any;

@Component({
  selector: 'app-item-add-wrapper',
  templateUrl: './item-add-wrapper.component.html',
  styleUrls: ['./item-add-wrapper.component.scss'],
})
export class ItemAddWrapperComponent implements OnInit {
  @Output() addItemEvent$ = new EventEmitter<any>();

  constructor(private readonly http: HttpClient) {}

  formType: string = 'Add';
  categories: [] = [];

  ngOnInit(): void {
    this.http
      .get<any>(`http://localhost:8080/guest/category?type=category`)
      .subscribe((categories) => {
        this.categories = categories;
      });
  }

  addItem(formData: FormData) {
    this.http
      .post<any>(
        `http://localhost:8080/admin/item/${formData.get('category')}`,
        formData
      )
      .subscribe((res) => {
        this.addItemEvent$.emit(res);
      });
  }
}
