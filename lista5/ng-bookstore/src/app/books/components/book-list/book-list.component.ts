import { Component } from '@angular/core';
import { Book } from '../../model/book';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { debounceTime, filter, Subject, switchMap } from 'rxjs';
import { BooksService } from '../../services/books.service';
import { FormControl } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
    selector: 'bs-book-list',
    templateUrl: './book-list.component.html',
    styleUrls: ['./book-list.component.scss'],
    standalone: true,
    imports: [RouterLink, ReactiveFormsModule]
})
export class BookListComponent {

  books: Book[];
  searchControl = new FormControl('');

  constructor(private readonly activatedRoute: ActivatedRoute, private readonly booksService: BooksService) {
    this.books = this.activatedRoute.snapshot.data['books'];
  }

  ngOnInit() {
    this.searchControl.valueChanges.pipe(
      debounceTime(200),
      filter(q => q == null ? false : true),
      switchMap(q => q == null || q.length < 2 ? this.booksService.getAllBooks() : this.booksService.searchBooks(q))
    ).subscribe(books => this.books = books);
  }
}
