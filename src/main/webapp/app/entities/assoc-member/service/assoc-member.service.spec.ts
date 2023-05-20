import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAssocMember } from '../assoc-member.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../assoc-member.test-samples';

import { AssocMemberService } from './assoc-member.service';

const requireRestSample: IAssocMember = {
  ...sampleWithRequiredData,
};

describe('AssocMember Service', () => {
  let service: AssocMemberService;
  let httpMock: HttpTestingController;
  let expectedResult: IAssocMember | IAssocMember[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AssocMemberService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a AssocMember', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const assocMember = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(assocMember).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AssocMember', () => {
      const assocMember = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(assocMember).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AssocMember', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AssocMember', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AssocMember', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAssocMemberToCollectionIfMissing', () => {
      it('should add a AssocMember to an empty array', () => {
        const assocMember: IAssocMember = sampleWithRequiredData;
        expectedResult = service.addAssocMemberToCollectionIfMissing([], assocMember);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(assocMember);
      });

      it('should not add a AssocMember to an array that contains it', () => {
        const assocMember: IAssocMember = sampleWithRequiredData;
        const assocMemberCollection: IAssocMember[] = [
          {
            ...assocMember,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAssocMemberToCollectionIfMissing(assocMemberCollection, assocMember);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AssocMember to an array that doesn't contain it", () => {
        const assocMember: IAssocMember = sampleWithRequiredData;
        const assocMemberCollection: IAssocMember[] = [sampleWithPartialData];
        expectedResult = service.addAssocMemberToCollectionIfMissing(assocMemberCollection, assocMember);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(assocMember);
      });

      it('should add only unique AssocMember to an array', () => {
        const assocMemberArray: IAssocMember[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const assocMemberCollection: IAssocMember[] = [sampleWithRequiredData];
        expectedResult = service.addAssocMemberToCollectionIfMissing(assocMemberCollection, ...assocMemberArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const assocMember: IAssocMember = sampleWithRequiredData;
        const assocMember2: IAssocMember = sampleWithPartialData;
        expectedResult = service.addAssocMemberToCollectionIfMissing([], assocMember, assocMember2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(assocMember);
        expect(expectedResult).toContain(assocMember2);
      });

      it('should accept null and undefined values', () => {
        const assocMember: IAssocMember = sampleWithRequiredData;
        expectedResult = service.addAssocMemberToCollectionIfMissing([], null, assocMember, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(assocMember);
      });

      it('should return initial array if no AssocMember is added', () => {
        const assocMemberCollection: IAssocMember[] = [sampleWithRequiredData];
        expectedResult = service.addAssocMemberToCollectionIfMissing(assocMemberCollection, undefined, null);
        expect(expectedResult).toEqual(assocMemberCollection);
      });
    });

    describe('compareAssocMember', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAssocMember(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAssocMember(entity1, entity2);
        const compareResult2 = service.compareAssocMember(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAssocMember(entity1, entity2);
        const compareResult2 = service.compareAssocMember(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAssocMember(entity1, entity2);
        const compareResult2 = service.compareAssocMember(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
