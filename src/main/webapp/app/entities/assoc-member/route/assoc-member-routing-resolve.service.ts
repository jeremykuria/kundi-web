import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAssocMember } from '../assoc-member.model';
import { AssocMemberService } from '../service/assoc-member.service';

@Injectable({ providedIn: 'root' })
export class AssocMemberRoutingResolveService implements Resolve<IAssocMember | null> {
  constructor(protected service: AssocMemberService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAssocMember | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((assocMember: HttpResponse<IAssocMember>) => {
          if (assocMember.body) {
            return of(assocMember.body);
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
