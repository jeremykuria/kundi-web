import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPledge } from '../pledge.model';
import { PledgeService } from '../service/pledge.service';

@Injectable({ providedIn: 'root' })
export class PledgeRoutingResolveService implements Resolve<IPledge | null> {
  constructor(protected service: PledgeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPledge | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pledge: HttpResponse<IPledge>) => {
          if (pledge.body) {
            return of(pledge.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
