import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AssociationComponent } from '../list/association.component';
import { AssociationDetailComponent } from '../detail/association-detail.component';
import { AssociationUpdateComponent } from '../update/association-update.component';
import { AssociationRoutingResolveService } from './association-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const associationRoute: Routes = [
  {
    path: '',
    component: AssociationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AssociationDetailComponent,
    resolve: {
      association: AssociationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AssociationUpdateComponent,
    resolve: {
      association: AssociationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AssociationUpdateComponent,
    resolve: {
      association: AssociationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(associationRoute)],
  exports: [RouterModule],
})
export class AssociationRoutingModule {}
