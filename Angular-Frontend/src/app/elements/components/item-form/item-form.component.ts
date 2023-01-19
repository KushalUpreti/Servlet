import {
  Component,
  EventEmitter,
  Input,
  Output,
  OnChanges,
  SimpleChanges,
} from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-item-form',
  templateUrl: './item-form.component.html',
  styleUrls: ['./item-form.component.scss'],
})
export class ItemFormComponent implements OnChanges {
  @Input() formType: string = '';
  @Input() categories: any = [];
  @Input() item: any = null;
  @Output() submitEvent$ = new EventEmitter<FormData>();
  @Output() deleteImageEvent$ = new EventEmitter<number>();

  itemForm = new FormGroup({
    title: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
    price: new FormControl('', [Validators.min(1)]),
  });

  selectedFiles?: FileList;
  title: string = '';
  description: string = '';
  price: number = 1;
  selected = -1;
  myFiles: string[] = [];

  ngOnChanges(changes: SimpleChanges) {
    const chng = changes['item'];
    if (!chng) {
      return;
    }
    const cur = chng.currentValue;
    const prev = chng.previousValue;
    if (!prev && cur) {
      this.title = this.item.title;
      this.description = this.item.description;
      this.price = this.item.price;
      this.selected = this.item.category.id;
    }
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
    formData.append('category', this.selected.toString());

    for (let i = 0; i < this.myFiles.length; i++) {
      formData.append('images', this.myFiles[i]);
    }

    this.submitEvent$.emit(formData);
    this.itemForm.reset();
  }

  deleteImage(imageId: number) {
    this.deleteImageEvent$.emit(imageId);
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
