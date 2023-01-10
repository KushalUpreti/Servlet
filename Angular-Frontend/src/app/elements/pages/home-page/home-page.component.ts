import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss'],
})
export class HomePageComponent implements OnInit {
  categories: any = [];
  carouselItems = [
    {
      imageURL:
        'https://static01.nyt.com/images/2018/12/14/autossell/dress/dress-master768-v2.jpg',
      title: "Women's Fashion",
      caption:
        'Pick outfits that fit the setting. Let your characters change outfits.',
    },
    {
      imageURL:
        'http://api.letsdiskuss.com/resources/static/asset/uploads/1650505823637-aviv-rachmadian-7F7kEHj72MQ-unsplash-scaled.jpg',
      title: "Women's Fashion",
      caption:
        'Pick outfits that fit the setting. Let your characters change outfits.',
    },
    {
      imageURL:
        'http://api.letsdiskuss.com/resources/static/asset/uploads/1650505823637-aviv-rachmadian-7F7kEHj72MQ-unsplash-scaled.jpg',
      title: "Women's Fashion",
      caption:
        'Pick outfits that fit the setting. Let your characters change outfits.',
    },
  ];
  showText: boolean = true;

  constructor(private readonly http: HttpClient) {}

  ngOnInit(): void {
    // this.http
    //   .get<any>(`http://localhost:8080/api/guest/category/`)
    //   .subscribe((categories) => {
    //     this.categories = categories;
    //   });
  }
}
