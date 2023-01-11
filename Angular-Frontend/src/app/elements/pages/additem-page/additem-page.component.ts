import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
declare var bootstrap: any;

@Component({
  selector: 'app-additem-page',
  templateUrl: './additem-page.component.html',
  styleUrls: ['./additem-page.component.scss'],
})
export class AdditemPageComponent implements OnInit {
  itemForm = new FormGroup({
    title: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
    price: new FormControl('', [Validators.min(1)]),
  });

  selectedFiles?: FileList;
  title: string;
  description: string;
  price: number;
  message: string = 'Added Item';
  categories = [];
  selected = -1;

  constructor(private readonly http: HttpClient) {}

  ngOnInit(): void {
    this.http
      .get<any>(`http://localhost:8080/guest/category?type=category`)
      .subscribe((categories) => {
        this.categories = categories;
      });
  }

  addItem() {
    this.http
      .post<any>(`http://localhost:8080/item`, {
        title: this.title,
        description: this.description,
        price: this.price,
        categoryId: +this.selected,
      })
      .subscribe((res) => {
        this.loadToast();
        this.itemForm.reset();
      });
  }

  selectFiles(event: any): void {
    this.selectedFiles = event.target.files;
  }

  getTitleErrorMessage() {
    if (this.itemForm.get('title').hasError('required')) {
      return 'You must enter a value';
    }
    return '';
  }
  getDescErrorMessage() {
    if (this.itemForm.get('description').hasError('required')) {
      return 'You must enter a value';
    }
    return '';
  }
  getPriceErrorMessage() {
    if (this.itemForm.get('price').hasError('min')) {
      return 'Enter min price of 1';
    }
    return '';
  }

  loadToast(): void {
    const toastLiveExample = document.getElementById('liveToast');
    const toast = new bootstrap.Toast(toastLiveExample);
    toast.show();
  }
}
