import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Review } from '../model/review';
import { HttpClient } from '@angular/common/http';

const booksApiPrefix = '/api/reviews';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(private readonly http: HttpClient) { }

  getReviewsForBook(bookId: number): Observable<Review[]> {
  return this.http.get<Review[]>(`${booksApiPrefix}?forBook=${bookId}`);
 }

  saveReview(review: Review): Observable<Review> {
    return this.http.post<Review>(booksApiPrefix,review);
 }
}
