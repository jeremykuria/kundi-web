import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../association.test-samples';

import { AssociationFormService } from './association-form.service';

describe('Association Form Service', () => {
  let service: AssociationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AssociationFormService);
  });

  describe('Service methods', () => {
    describe('createAssociationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAssociationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            brief: expect.any(Object),
            description: expect.any(Object),
            phoneNumber: expect.any(Object),
            contentStatus: expect.any(Object),
            paybill: expect.any(Object),
            associationType: expect.any(Object),
            configs: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            lastUpdatedOn: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            members: expect.any(Object),
          })
        );
      });

      it('passing IAssociation should create a new form with FormGroup', () => {
        const formGroup = service.createAssociationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            brief: expect.any(Object),
            description: expect.any(Object),
            phoneNumber: expect.any(Object),
            contentStatus: expect.any(Object),
            paybill: expect.any(Object),
            associationType: expect.any(Object),
            configs: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            lastUpdatedOn: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            members: expect.any(Object),
          })
        );
      });
    });

    describe('getAssociation', () => {
      it('should return NewAssociation for default Association initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAssociationFormGroup(sampleWithNewData);

        const association = service.getAssociation(formGroup) as any;

        expect(association).toMatchObject(sampleWithNewData);
      });

      it('should return NewAssociation for empty Association initial value', () => {
        const formGroup = service.createAssociationFormGroup();

        const association = service.getAssociation(formGroup) as any;

        expect(association).toMatchObject({});
      });

      it('should return IAssociation', () => {
        const formGroup = service.createAssociationFormGroup(sampleWithRequiredData);

        const association = service.getAssociation(formGroup) as any;

        expect(association).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAssociation should not enable id FormControl', () => {
        const formGroup = service.createAssociationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAssociation should disable id FormControl', () => {
        const formGroup = service.createAssociationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
