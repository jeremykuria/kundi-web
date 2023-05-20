import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AssocMemberComponent } from '../list/assoc-member.component';
import { AssocMemberDetailComponent } from '../detail/assoc-member-detail.component';
import { AssocMemberUpdateComponent } from '../update/assoc-member-update.component';
import { AssocMemberRoutingResolveService } from './assoc-member-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const assocMemberRoute: Routes = [
  {
    path: '',
    component: AssocMemberComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AssocMemberDetailComponent,
    resolve: {
      assocMember: AssocMemberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AssocMemberUpdateComponent,
    resolve: {
      assocMember: AssocMemberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AssocMemberUpdateComponent,
    resolve: {
      assocMember: AssocMemberRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(assocMemberRoute)],
  exports: [RouterModule],
})
export class AssocMemberRoutingModule {}
