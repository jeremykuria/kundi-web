import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDailyReport, NewDailyReport } from '../daily-report.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDailyReport for edit and NewDailyReportFormGroupInput for create.
 */
type DailyReportFormGroupInput = IDailyReport | PartialWithRequiredKeyOf<NewDailyReport>;

type DailyReportFormDefaults = Pick<NewDailyReport, 'id'>;

type DailyReportFormGroupContent = {
  id: FormControl<IDailyReport['id'] | NewDailyReport['id']>;
  reportDate: FormControl<IDailyReport['reportDate']>;
  succesfulTrx: FormControl<IDailyReport['succesfulTrx']>;
  failedTrx: FormControl<IDailyReport['failedTrx']>;
  amount: FormControl<IDailyReport['amount']>;
  configs: FormControl<IDailyReport['configs']>;
  createdOn: FormControl<IDailyReport['createdOn']>;
  createdBy: FormControl<IDailyReport['createdBy']>;
  lastUpdatedOn: FormControl<IDailyReport['lastUpdatedOn']>;
  lastUpdatedBy: FormControl<IDailyReport['lastUpdatedBy']>;
};

export type DailyReportFormGroup = FormGroup<DailyReportFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DailyReportFormService {
  createDailyReportFormGroup(dailyReport: DailyReportFormGroupInput = { id: null }): DailyReportFormGroup {
    const dailyReportRawValue = {
      ...this.getFormDefaults(),
      ...dailyReport,
    };
    return new FormGroup<DailyReportFormGroupContent>({
      id: new FormControl(
        { value: dailyReportRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      reportDate: new FormControl(dailyReportRawValue.reportDate),
      succesfulTrx: new FormControl(dailyReportRawValue.succesfulTrx),
      failedTrx: new FormControl(dailyReportRawValue.failedTrx),
      amount: new FormControl(dailyReportRawValue.amount),
      configs: new FormControl(dailyReportRawValue.configs),
      createdOn: new FormControl(dailyReportRawValue.createdOn),
      createdBy: new FormControl(dailyReportRawValue.createdBy),
      lastUpdatedOn: new FormControl(dailyReportRawValue.lastUpdatedOn),
      lastUpdatedBy: new FormControl(dailyReportRawValue.lastUpdatedBy),
    });
  }

  getDailyReport(form: DailyReportFormGroup): IDailyReport | NewDailyReport {
    return form.getRawValue() as IDailyReport | NewDailyReport;
  }

  resetForm(form: DailyReportFormGroup, dailyReport: DailyReportFormGroupInput): void {
    const dailyReportRawValue = { ...this.getFormDefaults(), ...dailyReport };
    form.reset(
      {
        ...dailyReportRawValue,
        id: { value: dailyReportRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DailyReportFormDefaults {
    return {
      id: null,
    };
  }
}
