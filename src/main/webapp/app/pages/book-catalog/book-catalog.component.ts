import { Component, OnInit } from '@angular/core';
import { IBookCatalogItemOnDisplay } from './book-catalog.model';
import { BookCatalogService } from './book-catalog.service';
import { IBook } from 'app/entities/book/book.model';
import { IBookOnDisplay, convertBookToBookOnDisplay } from 'app/components/book-display/book-display.model';

@Component({
  selector: 'jhi-book-catalog',
  templateUrl: './book-catalog.component.html',
  styleUrls: ['./book-catalog.component.scss'],
})
export class BookCatalogComponent implements OnInit {
  catalog: IBookCatalogItemOnDisplay[] = [];

  constructor(protected bookCatalogService: BookCatalogService) {}

  ngOnInit(): void {
    this.load();
  }

  load() {
    this.bookCatalogService.getTopCategories().subscribe(response => {
      this.catalog = response.body!.map(category => {
        return {
          categoryId: category.id,
          categoryName: category.name!,
        };
      });

      for (let catalogItem of this.catalog) {
        this.bookCatalogService
          .getBooksByCategory({
            id: catalogItem.categoryId,
            page: 0,
            size: 100,
          })
          .subscribe(response => {
            const books: IBook[] = response.body!;

            // slice whole book list to separate slides to be displayed on carousel
            const content: IBookOnDisplay[][] = [];
            const booksPerSlide = 5;
            for (let i = 0; i < books.length; i += booksPerSlide) {
              content.push(books.slice(i, i + booksPerSlide).map(book => convertBookToBookOnDisplay(book)));
            }
            catalogItem.content = content;
          });
      }
    });
  }
}
