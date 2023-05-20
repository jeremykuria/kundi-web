import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../assoc-member.test-samples';

import { AssocMemberFormService } from './assoc-member-form.service';

describe('AssocMember Form Service', () => {
  let service: AssocMemberFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AssocMemberFormService);
  });

  describe('Service methods', () => {
    describe('createAssocMemberFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAssocMemberFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            email: expect.any(Object),
            phone: expect.any(Object),
            year: expect.any(Object),
            regNo: expect.any(Object),
            role: expect.any(Object),
            configs: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            lastUpdatedOn: expect.any(Object),
            pledge: expect.any(Object),
            associations: expect.any(Object),
          })
        );
      });

      it('passing IAssocMember should create a new form with FormGroup', () => {
        const formGroup = service.createAssocMemberFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            email: expect.any(Object),
            phone: expect.any(Object),
            year: expect.any(Object),
            regNo: expect.any(Object),
            role: expect.any(Object),
            configs: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            lastUpdatedOn: expect.any(Object),
            pledge: expect.any(Object),
            associations: expect.any(Object),
          })
        );
      });
    });

    describe('getAssocMember', () => {
      it('should return NewAssocMember for default AssocMember initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAssocMemberFormGroup(sampleWithNewData);

        const assocMember = service.getAssocMember(formGroup) as any;

        expect(assocMember).toMatchObject(sampleWithNewData);
      });

      it('should return NewAssocMember for empty AssocMember initial value', () => {
        const formGroup = service.createAssocMemberFormGroup();

        const assocMember = service.getAssocMember(formGroup) as any;

        expect(assocMember).toMatchObject({});
      });

      it('should return IAssocMember', () => {
        const formGroup = service.createAssocMemberFormGroup(sampleWithRequiredData);

        const assocMember = service.getAssocMember(formGroup) as any;

        expect(assocMember).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAssocMember should not enable id FormControl', () => {
        const formGroup = service.createAssocMemberFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAssocMember should disable id FormControl', () => {
        const formGroup = service.createAssocMemberFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
