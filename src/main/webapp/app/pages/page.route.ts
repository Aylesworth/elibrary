import { Routes } from '@angular/router';
import { BookCatalogComponent } from './book-catalog/book-catalog.component';
import { BookSearchComponent } from './book-search/book-search.component';

export const pageRoute: Routes = [
  {
    path: 'catalog',
    component: BookCatalogComponent,
  },
  {
    path: 'search',
    component: BookSearchComponent,
  },
];
