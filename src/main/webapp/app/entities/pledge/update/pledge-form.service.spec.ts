import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../pledge.test-samples';

import { PledgeFormService } from './pledge-form.service';

describe('Pledge Form Service', () => {
  let service: PledgeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PledgeFormService);
  });

  describe('Service methods', () => {
    describe('createPledgeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPledgeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            targetAmount: expect.any(Object),
            paidAmount: expect.any(Object),
            status: expect.any(Object),
            noOfReminders: expect.any(Object),
            reminderCycle: expect.any(Object),
            configs: expect.any(Object),
            createdOn: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedOn: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            project: expect.any(Object),
          })
        );
      });

      it('passing IPledge should create a new form with FormGroup', () => {
        const formGroup = service.createPledgeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            targetAmount: expect.any(Object),
            paidAmount: expect.any(Object),
            status: expect.any(Object),
            noOfReminders: expect.any(Object),
            reminderCycle: expect.any(Object),
            configs: expect.any(Object),
            createdOn: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedOn: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            project: expect.any(Object),
          })
        );
      });
    });

    describe('getPledge', () => {
      it('should return NewPledge for default Pledge initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPledgeFormGroup(sampleWithNewData);

        const pledge = service.getPledge(formGroup) as any;

        expect(pledge).toMatchObject(sampleWithNewData);
      });

      it('should return NewPledge for empty Pledge initial value', () => {
        const formGroup = service.createPledgeFormGroup();

        const pledge = service.getPledge(formGroup) as any;

        expect(pledge).toMatchObject({});
      });

      it('should return IPledge', () => {
        const formGroup = service.createPledgeFormGroup(sampleWithRequiredData);

        const pledge = service.getPledge(formGroup) as any;

        expect(pledge).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPledge should not enable id FormControl', () => {
        const formGroup = service.createPledgeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPledge should disable id FormControl', () => {
        const formGroup = service.createPledgeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
