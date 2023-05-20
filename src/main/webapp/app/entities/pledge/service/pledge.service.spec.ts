import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPledge } from '../pledge.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../pledge.test-samples';

import { PledgeService } from './pledge.service';

const requireRestSample: IPledge = {
  ...sampleWithRequiredData,
};

describe('Pledge Service', () => {
  let service: PledgeService;
  let httpMock: HttpTestingController;
  let expectedResult: IPledge | IPledge[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PledgeService);
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

    it('should create a Pledge', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const pledge = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(pledge).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Pledge', () => {
      const pledge = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(pledge).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Pledge', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Pledge', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Pledge', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPledgeToCollectionIfMissing', () => {
      it('should add a Pledge to an empty array', () => {
        const pledge: IPledge = sampleWithRequiredData;
        expectedResult = service.addPledgeToCollectionIfMissing([], pledge);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pledge);
      });

      it('should not add a Pledge to an array that contains it', () => {
        const pledge: IPledge = sampleWithRequiredData;
        const pledgeCollection: IPledge[] = [
          {
            ...pledge,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPledgeToCollectionIfMissing(pledgeCollection, pledge);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Pledge to an array that doesn't contain it", () => {
        const pledge: IPledge = sampleWithRequiredData;
        const pledgeCollection: IPledge[] = [sampleWithPartialData];
        expectedResult = service.addPledgeToCollectionIfMissing(pledgeCollection, pledge);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pledge);
      });

      it('should add only unique Pledge to an array', () => {
        const pledgeArray: IPledge[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const pledgeCollection: IPledge[] = [sampleWithRequiredData];
        expectedResult = service.addPledgeToCollectionIfMissing(pledgeCollection, ...pledgeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pledge: IPledge = sampleWithRequiredData;
        const pledge2: IPledge = sampleWithPartialData;
        expectedResult = service.addPledgeToCollectionIfMissing([], pledge, pledge2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pledge);
        expect(expectedResult).toContain(pledge2);
      });

      it('should accept null and undefined values', () => {
        const pledge: IPledge = sampleWithRequiredData;
        expectedResult = service.addPledgeToCollectionIfMissing([], null, pledge, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pledge);
      });

      it('should return initial array if no Pledge is added', () => {
        const pledgeCollection: IPledge[] = [sampleWithRequiredData];
        expectedResult = service.addPledgeToCollectionIfMissing(pledgeCollection, undefined, null);
        expect(expectedResult).toEqual(pledgeCollection);
      });
    });

    describe('comparePledge', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePledge(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePledge(entity1, entity2);
        const compareResult2 = service.comparePledge(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePledge(entity1, entity2);
        const compareResult2 = service.comparePledge(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePledge(entity1, entity2);
        const compareResult2 = service.comparePledge(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
