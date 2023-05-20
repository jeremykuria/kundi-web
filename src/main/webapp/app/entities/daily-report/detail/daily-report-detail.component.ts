import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDailyReport } from '../daily-report.model';

@Component({
  selector: 'jhi-daily-report-detail',
  templateUrl: './daily-report-detail.component.html',
})
export class DailyReportDetailComponent implements OnInit {
  dailyReport: IDailyReport | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dailyReport }) => {
      this.dailyReport = dailyReport;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
