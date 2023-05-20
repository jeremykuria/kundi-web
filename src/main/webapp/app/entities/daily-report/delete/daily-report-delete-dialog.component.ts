import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDailyReport } from '../daily-report.model';
import { DailyReportService } from '../service/daily-report.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './daily-report-delete-dialog.component.html',
})
export class DailyReportDeleteDialogComponent {
  dailyReport?: IDailyReport;

  constructor(protected dailyReportService: DailyReportService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dailyReportService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
