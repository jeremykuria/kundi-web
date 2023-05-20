import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PledgeComponent } from '../list/pledge.component';
import { PledgeDetailComponent } from '../detail/pledge-detail.component';
import { PledgeUpdateComponent } from '../update/pledge-update.component';
import { PledgeRoutingResolveService } from './pledge-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const pledgeRoute: Routes = [
  {
    path: '',
    component: PledgeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PledgeDetailComponent,
    resolve: {
      pledge: PledgeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PledgeUpdateComponent,
    resolve: {
      pledge: PledgeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PledgeUpdateComponent,
    resolve: {
      pledge: PledgeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pledgeRoute)],
  exports: [RouterModule],
})
export class PledgeRoutingModule {}
