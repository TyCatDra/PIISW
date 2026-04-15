import { TestBed } from '@angular/core/testing';
import { ResolveFn } from '@angular/router';

import { bookDetailsResolver } from './book-details.resolver';

describe('bookDetailsResolver', () => {
  const executeResolver: ResolveFn<boolean> = (...resolverParameters) => 
      TestBed.runInInjectionContext(() => bookDetailsResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeResolver).toBeTruthy();
  });
});
