import { TestBed } from '@angular/core/testing';
import { ResolveFn } from '@angular/router';

import { reviewResolverResolver } from './review-resolver.resolver';

describe('reviewResolverResolver', () => {
  const executeResolver: ResolveFn<boolean> = (...resolverParameters) => 
      TestBed.runInInjectionContext(() => reviewResolverResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeResolver).toBeTruthy();
  });
});
