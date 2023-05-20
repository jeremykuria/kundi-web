import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDailyReport } from '../daily-report.model';
import { DailyReportService } from '../service/daily-report.service';

@Injectable({ providedIn: 'root' })
export class DailyReportRoutingResolveService implements Resolve<IDailyReport | null> {
  constructor(protected service: DailyReportService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDailyReport | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dailyReport: HttpResponse<IDailyReport>) => {
          if (dailyReport.body) {
            return of(dailyReport.body);
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
