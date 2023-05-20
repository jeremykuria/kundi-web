import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAssociation, NewAssociation } from '../association.model';

export type PartialUpdateAssociation = Partial<IAssociation> & Pick<IAssociation, 'id'>;

export type EntityResponseType = HttpResponse<IAssociation>;
export type EntityArrayResponseType = HttpResponse<IAssociation[]>;

@Injectable({ providedIn: 'root' })
export class AssociationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/associations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(association: NewAssociation): Observable<EntityResponseType> {
    return this.http.post<IAssociation>(this.resourceUrl, association, { observe: 'response' });
  }

  update(association: IAssociation): Observable<EntityResponseType> {
    return this.http.put<IAssociation>(`${this.resourceUrl}/${this.getAssociationIdentifier(association)}`, association, {
      observe: 'response',
    });
  }

  partialUpdate(association: PartialUpdateAssociation): Observable<EntityResponseType> {
    return this.http.patch<IAssociation>(`${this.resourceUrl}/${this.getAssociationIdentifier(association)}`, association, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAssociation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAssociation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAssociationIdentifier(association: Pick<IAssociation, 'id'>): number {
    return association.id;
  }

  compareAssociation(o1: Pick<IAssociation, 'id'> | null, o2: Pick<IAssociation, 'id'> | null): boolean {
    return o1 && o2 ? this.getAssociationIdentifier(o1) === this.getAssociationIdentifier(o2) : o1 === o2;
  }

  addAssociationToCollectionIfMissing<Type extends Pick<IAssociation, 'id'>>(
    associationCollection: Type[],
    ...associationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const associations: Type[] = associationsToCheck.filter(isPresent);
    if (associations.length > 0) {
      const associationCollectionIdentifiers = associationCollection.map(
        associationItem => this.getAssociationIdentifier(associationItem)!
      );
      const associationsToAdd = associations.filter(associationItem => {
        const associationIdentifier = this.getAssociationIdentifier(associationItem);
        if (associationCollectionIdentifiers.includes(associationIdentifier)) {
          return false;
        }
        associationCollectionIdentifiers.push(associationIdentifier);
        return true;
      });
      return [...associationsToAdd, ...associationCollection];
    }
    return associationCollection;
  }
}
