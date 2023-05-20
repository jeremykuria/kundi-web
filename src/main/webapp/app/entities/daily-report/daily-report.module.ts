import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DailyReportComponent } from './list/daily-report.component';
import { DailyReportDetailComponent } from './detail/daily-report-detail.component';
import { DailyReportUpdateComponent } from './update/daily-report-update.component';
import { DailyReportDeleteDialogComponent } from './delete/daily-report-delete-dialog.component';
import { DailyReportRoutingModule } from './route/daily-report-routing.module';

@NgModule({
  imports: [SharedModule, DailyReportRoutingModule],
  declarations: [DailyReportComponent, DailyReportDetailComponent, DailyReportUpdateComponent, DailyReportDeleteDialogComponent],
})
export class DailyReportModule {}
