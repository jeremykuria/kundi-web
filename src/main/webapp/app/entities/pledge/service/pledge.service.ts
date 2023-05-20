import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPledge, NewPledge } from '../pledge.model';

export type PartialUpdatePledge = Partial<IPledge> & Pick<IPledge, 'id'>;

export type EntityResponseType = HttpResponse<IPledge>;
export type EntityArrayResponseType = HttpResponse<IPledge[]>;

@Injectable({ providedIn: 'root' })
export class PledgeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pledges');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pledge: NewPledge): Observable<EntityResponseType> {
    return this.http.post<IPledge>(this.resourceUrl, pledge, { observe: 'response' });
  }

  update(pledge: IPledge): Observable<EntityResponseType> {
    return this.http.put<IPledge>(`${this.resourceUrl}/${this.getPledgeIdentifier(pledge)}`, pledge, { observe: 'response' });
  }

  partialUpdate(pledge: PartialUpdatePledge): Observable<EntityResponseType> {
    return this.http.patch<IPledge>(`${this.resourceUrl}/${this.getPledgeIdentifier(pledge)}`, pledge, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPledge>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPledge[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPledgeIdentifier(pledge: Pick<IPledge, 'id'>): number {
    return pledge.id;
  }

  comparePledge(o1: Pick<IPledge, 'id'> | null, o2: Pick<IPledge, 'id'> | null): boolean {
    return o1 && o2 ? this.getPledgeIdentifier(o1) === this.getPledgeIdentifier(o2) : o1 === o2;
  }

  addPledgeToCollectionIfMissing<Type extends Pick<IPledge, 'id'>>(
    pledgeCollection: Type[],
    ...pledgesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const pledges: Type[] = pledgesToCheck.filter(isPresent);
    if (pledges.length > 0) {
      const pledgeCollectionIdentifiers = pledgeCollection.map(pledgeItem => this.getPledgeIdentifier(pledgeItem)!);
      const pledgesToAdd = pledges.filter(pledgeItem => {
        const pledgeIdentifier = this.getPledgeIdentifier(pledgeItem);
        if (pledgeCollectionIdentifiers.includes(pledgeIdentifier)) {
          return false;
        }
        pledgeCollectionIdentifiers.push(pledgeIdentifier);
        return true;
      });
      return [...pledgesToAdd, ...pledgeCollection];
    }
    return pledgeCollection;
  }
}
