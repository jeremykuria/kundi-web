import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { DailyReportFormService, DailyReportFormGroup } from './daily-report-form.service';
import { IDailyReport } from '../daily-report.model';
import { DailyReportService } from '../service/daily-report.service';

@Component({
  selector: 'jhi-daily-report-update',
  templateUrl: './daily-report-update.component.html',
})
export class DailyReportUpdateComponent implements OnInit {
  isSaving = false;
  dailyReport: IDailyReport | null = null;

  editForm: DailyReportFormGroup = this.dailyReportFormService.createDailyReportFormGroup();

  constructor(
    protected dailyReportService: DailyReportService,
    protected dailyReportFormService: DailyReportFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dailyReport }) => {
      this.dailyReport = dailyReport;
      if (dailyReport) {
        this.updateForm(dailyReport);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dailyReport = this.dailyReportFormService.getDailyReport(this.editForm);
    if (dailyReport.id !== null) {
      this.subscribeToSaveResponse(this.dailyReportService.update(dailyReport));
    } else {
      this.subscribeToSaveResponse(this.dailyReportService.create(dailyReport));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDailyReport>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(dailyReport: IDailyReport): void {
    this.dailyReport = dailyReport;
    this.dailyReportFormService.resetForm(this.editForm, dailyReport);
  }
}
