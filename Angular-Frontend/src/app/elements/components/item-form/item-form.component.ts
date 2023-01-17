import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-item-form',
  templateUrl: './item-form.component.html',
  styleUrls: ['./item-form.component.scss'],
})
export class ItemFormComponent {
  itemForm = new FormGroup({
    title: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
    price: new FormControl('', [Validators.min(1)]),
  });

  selectedFiles?: FileList;
  title: string;
  description: string;
  price: number;
  categories = [];
  selected = -1;
  items = [];

  myFiles: string[] = [];

  constructor(
    private readonly http: HttpClient,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.http
      .get<any>(`http://localhost:8080/guest/category?type=category`)
      .subscribe((categories) => {
        this.categories = categories;
      });

    this.http
      .get<any>(`http://localhost:8080/admin/item`)
      .subscribe((items) => {
        this.items = items;
      });
  }

  onFileChange(event: any) {
    for (let i = 0; i < event.target.files.length; i++) {
      this.myFiles.push(event.target.files[i]);
    }
  }

  addItem() {
    const formData = new FormData();
    formData.append('title', this.title);
    formData.append('description', this.description);
    formData.append('price', this.price.toString());

    for (let i = 0; i < this.myFiles.length; i++) {
      formData.append('images', this.myFiles[i]);
    }

    this.http
      .post<any>(`http://localhost:8080/admin/item/${this.selected}`, formData)
      .subscribe((res) => {
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
}
