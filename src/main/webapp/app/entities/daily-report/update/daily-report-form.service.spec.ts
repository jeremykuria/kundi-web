import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../daily-report.test-samples';

import { DailyReportFormService } from './daily-report-form.service';

describe('DailyReport Form Service', () => {
  let service: DailyReportFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DailyReportFormService);
  });

  describe('Service methods', () => {
    describe('createDailyReportFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDailyReportFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            reportDate: expect.any(Object),
            succesfulTrx: expect.any(Object),
            failedTrx: expect.any(Object),
            amount: expect.any(Object),
            configs: expect.any(Object),
            createdOn: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedOn: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
          })
        );
      });

      it('passing IDailyReport should create a new form with FormGroup', () => {
        const formGroup = service.createDailyReportFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            reportDate: expect.any(Object),
            succesfulTrx: expect.any(Object),
            failedTrx: expect.any(Object),
            amount: expect.any(Object),
            configs: expect.any(Object),
            createdOn: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedOn: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
          })
        );
      });
    });

    describe('getDailyReport', () => {
      it('should return NewDailyReport for default DailyReport initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDailyReportFormGroup(sampleWithNewData);

        const dailyReport = service.getDailyReport(formGroup) as any;

        expect(dailyReport).toMatchObject(sampleWithNewData);
      });

      it('should return NewDailyReport for empty DailyReport initial value', () => {
        const formGroup = service.createDailyReportFormGroup();

        const dailyReport = service.getDailyReport(formGroup) as any;

        expect(dailyReport).toMatchObject({});
      });

      it('should return IDailyReport', () => {
        const formGroup = service.createDailyReportFormGroup(sampleWithRequiredData);

        const dailyReport = service.getDailyReport(formGroup) as any;

        expect(dailyReport).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDailyReport should not enable id FormControl', () => {
        const formGroup = service.createDailyReportFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDailyReport should disable id FormControl', () => {
        const formGroup = service.createDailyReportFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
