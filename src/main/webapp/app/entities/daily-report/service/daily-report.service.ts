import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDailyReport, NewDailyReport } from '../daily-report.model';

export type PartialUpdateDailyReport = Partial<IDailyReport> & Pick<IDailyReport, 'id'>;

export type EntityResponseType = HttpResponse<IDailyReport>;
export type EntityArrayResponseType = HttpResponse<IDailyReport[]>;

@Injectable({ providedIn: 'root' })
export class DailyReportService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/daily-reports');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dailyReport: NewDailyReport): Observable<EntityResponseType> {
    return this.http.post<IDailyReport>(this.resourceUrl, dailyReport, { observe: 'response' });
  }

  update(dailyReport: IDailyReport): Observable<EntityResponseType> {
    return this.http.put<IDailyReport>(`${this.resourceUrl}/${this.getDailyReportIdentifier(dailyReport)}`, dailyReport, {
      observe: 'response',
    });
  }

  partialUpdate(dailyReport: PartialUpdateDailyReport): Observable<EntityResponseType> {
    return this.http.patch<IDailyReport>(`${this.resourceUrl}/${this.getDailyReportIdentifier(dailyReport)}`, dailyReport, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDailyReport>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDailyReport[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDailyReportIdentifier(dailyReport: Pick<IDailyReport, 'id'>): number {
    return dailyReport.id;
  }

  compareDailyReport(o1: Pick<IDailyReport, 'id'> | null, o2: Pick<IDailyReport, 'id'> | null): boolean {
    return o1 && o2 ? this.getDailyReportIdentifier(o1) === this.getDailyReportIdentifier(o2) : o1 === o2;
  }

  addDailyReportToCollectionIfMissing<Type extends Pick<IDailyReport, 'id'>>(
    dailyReportCollection: Type[],
    ...dailyReportsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const dailyReports: Type[] = dailyReportsToCheck.filter(isPresent);
    if (dailyReports.length > 0) {
      const dailyReportCollectionIdentifiers = dailyReportCollection.map(
        dailyReportItem => this.getDailyReportIdentifier(dailyReportItem)!
      );
      const dailyReportsToAdd = dailyReports.filter(dailyReportItem => {
        const dailyReportIdentifier = this.getDailyReportIdentifier(dailyReportItem);
        if (dailyReportCollectionIdentifiers.includes(dailyReportIdentifier)) {
          return false;
        }
        dailyReportCollectionIdentifiers.push(dailyReportIdentifier);
        return true;
      });
      return [...dailyReportsToAdd, ...dailyReportCollection];
    }
    return dailyReportCollection;
  }
}
