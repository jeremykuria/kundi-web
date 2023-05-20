import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDailyReport } from '../daily-report.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../daily-report.test-samples';

import { DailyReportService } from './daily-report.service';

const requireRestSample: IDailyReport = {
  ...sampleWithRequiredData,
};

describe('DailyReport Service', () => {
  let service: DailyReportService;
  let httpMock: HttpTestingController;
  let expectedResult: IDailyReport | IDailyReport[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DailyReportService);
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

    it('should create a DailyReport', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const dailyReport = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(dailyReport).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DailyReport', () => {
      const dailyReport = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(dailyReport).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DailyReport', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DailyReport', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DailyReport', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDailyReportToCollectionIfMissing', () => {
      it('should add a DailyReport to an empty array', () => {
        const dailyReport: IDailyReport = sampleWithRequiredData;
        expectedResult = service.addDailyReportToCollectionIfMissing([], dailyReport);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dailyReport);
      });

      it('should not add a DailyReport to an array that contains it', () => {
        const dailyReport: IDailyReport = sampleWithRequiredData;
        const dailyReportCollection: IDailyReport[] = [
          {
            ...dailyReport,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDailyReportToCollectionIfMissing(dailyReportCollection, dailyReport);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DailyReport to an array that doesn't contain it", () => {
        const dailyReport: IDailyReport = sampleWithRequiredData;
        const dailyReportCollection: IDailyReport[] = [sampleWithPartialData];
        expectedResult = service.addDailyReportToCollectionIfMissing(dailyReportCollection, dailyReport);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dailyReport);
      });

      it('should add only unique DailyReport to an array', () => {
        const dailyReportArray: IDailyReport[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const dailyReportCollection: IDailyReport[] = [sampleWithRequiredData];
        expectedResult = service.addDailyReportToCollectionIfMissing(dailyReportCollection, ...dailyReportArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dailyReport: IDailyReport = sampleWithRequiredData;
        const dailyReport2: IDailyReport = sampleWithPartialData;
        expectedResult = service.addDailyReportToCollectionIfMissing([], dailyReport, dailyReport2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dailyReport);
        expect(expectedResult).toContain(dailyReport2);
      });

      it('should accept null and undefined values', () => {
        const dailyReport: IDailyReport = sampleWithRequiredData;
        expectedResult = service.addDailyReportToCollectionIfMissing([], null, dailyReport, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dailyReport);
      });

      it('should return initial array if no DailyReport is added', () => {
        const dailyReportCollection: IDailyReport[] = [sampleWithRequiredData];
        expectedResult = service.addDailyReportToCollectionIfMissing(dailyReportCollection, undefined, null);
        expect(expectedResult).toEqual(dailyReportCollection);
      });
    });

    describe('compareDailyReport', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDailyReport(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDailyReport(entity1, entity2);
        const compareResult2 = service.compareDailyReport(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDailyReport(entity1, entity2);
        const compareResult2 = service.compareDailyReport(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDailyReport(entity1, entity2);
        const compareResult2 = service.compareDailyReport(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
