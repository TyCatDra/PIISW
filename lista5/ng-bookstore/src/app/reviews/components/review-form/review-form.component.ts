import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ReviewService } from '../../services/review-service.service';
import { Review } from '../../model/review';

@Component({
  selector: 'bs-review-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './review-form.component.html',
  styleUrl: './review-form.component.scss'
})
export class ReviewFormComponent implements OnInit {
  @Input() bookId!: number;
  @Output() reviewAdded = new EventEmitter<void>();

  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private reviewService: ReviewService
  ) {}

  ngOnInit() {
    this.form = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      rate: [
        null,
        [Validators.required, Validators.min(1), Validators.max(5)]
      ]
    });
  }

  save() {
    if (this.form.invalid) return;

    const review: Review = {
      id: 0,
      forBook: this.bookId,
      ...this.form.value
    };

    this.reviewService.saveReview(review).subscribe(
      () => {
        this.form.reset();
        this.reviewAdded.emit();
      }
    );
  }
}
