import { Component, inject } from '@angular/core';
import { Book } from '../../model/book';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { NgIf } from '@angular/common';
import { Review } from '../../../reviews/model/review';
import { ReviewItemComponent } from '../../../reviews/components/review-item/review-item.component';
import { ReviewService } from '../../../reviews/services/review-service.service';
import { ReviewFormComponent } from '../../../reviews/components/review-form/review-form.component';

@Component({
  selector: 'bs-book-details',
  standalone: true,
  templateUrl: './book-details.component.html',
  styleUrl: './book-details.component.scss',
  imports: [RouterLink, NgIf, ReviewItemComponent, ReviewFormComponent]
})
export class BookDetailsComponent {
  book!: Book;
  reviews!: Review[];

  constructor(private readonly route: ActivatedRoute, private readonly reviewService: ReviewService) {
    this.book = this.route.snapshot.data['book'];
    this.reviews = this.route.snapshot.data['reviews'];
  }

  loadReviews() {
    this.reviewService
    .getReviewsForBook(this.book.id)
    .subscribe(r => (this.reviews = r));
 }
}
