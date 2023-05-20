import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'association',
        data: { pageTitle: 'portalApp.association.home.title' },
        loadChildren: () => import('./association/association.module').then(m => m.AssociationModule),
      },
      {
        path: 'assoc-member',
        data: { pageTitle: 'portalApp.assocMember.home.title' },
        loadChildren: () => import('./assoc-member/assoc-member.module').then(m => m.AssocMemberModule),
      },
      {
        path: 'project',
        data: { pageTitle: 'portalApp.project.home.title' },
        loadChildren: () => import('./project/project.module').then(m => m.ProjectModule),
      },
      {
        path: 'payment',
        data: { pageTitle: 'portalApp.payment.home.title' },
        loadChildren: () => import('./payment/payment.module').then(m => m.PaymentModule),
      },
      {
        path: 'pledge',
        data: { pageTitle: 'portalApp.pledge.home.title' },
        loadChildren: () => import('./pledge/pledge.module').then(m => m.PledgeModule),
      },
      {
        path: 'daily-report',
        data: { pageTitle: 'portalApp.dailyReport.home.title' },
        loadChildren: () => import('./daily-report/daily-report.module').then(m => m.DailyReportModule),
      },
      {
        path: 'notification',
        data: { pageTitle: 'portalApp.notification.home.title' },
        loadChildren: () => import('./notification/notification.module').then(m => m.NotificationModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
