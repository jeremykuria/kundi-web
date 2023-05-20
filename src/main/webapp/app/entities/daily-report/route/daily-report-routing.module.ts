import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DailyReportComponent } from '../list/daily-report.component';
import { DailyReportDetailComponent } from '../detail/daily-report-detail.component';
import { DailyReportUpdateComponent } from '../update/daily-report-update.component';
import { DailyReportRoutingResolveService } from './daily-report-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const dailyReportRoute: Routes = [
  {
    path: '',
    component: DailyReportComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DailyReportDetailComponent,
    resolve: {
      dailyReport: DailyReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DailyReportUpdateComponent,
    resolve: {
      dailyReport: DailyReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DailyReportUpdateComponent,
    resolve: {
      dailyReport: DailyReportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dailyReportRoute)],
  exports: [RouterModule],
})
export class DailyReportRoutingModule {}
