import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAssocMember, NewAssocMember } from '../assoc-member.model';

export type PartialUpdateAssocMember = Partial<IAssocMember> & Pick<IAssocMember, 'id'>;

export type EntityResponseType = HttpResponse<IAssocMember>;
export type EntityArrayResponseType = HttpResponse<IAssocMember[]>;

@Injectable({ providedIn: 'root' })
export class AssocMemberService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/assoc-members');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(assocMember: NewAssocMember): Observable<EntityResponseType> {
    return this.http.post<IAssocMember>(this.resourceUrl, assocMember, { observe: 'response' });
  }

  update(assocMember: IAssocMember): Observable<EntityResponseType> {
    return this.http.put<IAssocMember>(`${this.resourceUrl}/${this.getAssocMemberIdentifier(assocMember)}`, assocMember, {
      observe: 'response',
    });
  }

  partialUpdate(assocMember: PartialUpdateAssocMember): Observable<EntityResponseType> {
    return this.http.patch<IAssocMember>(`${this.resourceUrl}/${this.getAssocMemberIdentifier(assocMember)}`, assocMember, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAssocMember>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAssocMember[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAssocMemberIdentifier(assocMember: Pick<IAssocMember, 'id'>): number {
    return assocMember.id;
  }

  compareAssocMember(o1: Pick<IAssocMember, 'id'> | null, o2: Pick<IAssocMember, 'id'> | null): boolean {
    return o1 && o2 ? this.getAssocMemberIdentifier(o1) === this.getAssocMemberIdentifier(o2) : o1 === o2;
  }

  addAssocMemberToCollectionIfMissing<Type extends Pick<IAssocMember, 'id'>>(
    assocMemberCollection: Type[],
    ...assocMembersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const assocMembers: Type[] = assocMembersToCheck.filter(isPresent);
    if (assocMembers.length > 0) {
      const assocMemberCollectionIdentifiers = assocMemberCollection.map(
        assocMemberItem => this.getAssocMemberIdentifier(assocMemberItem)!
      );
      const assocMembersToAdd = assocMembers.filter(assocMemberItem => {
        const assocMemberIdentifier = this.getAssocMemberIdentifier(assocMemberItem);
        if (assocMemberCollectionIdentifiers.includes(assocMemberIdentifier)) {
          return false;
        }
        assocMemberCollectionIdentifiers.push(assocMemberIdentifier);
        return true;
      });
      return [...assocMembersToAdd, ...assocMemberCollection];
    }
    return assocMemberCollection;
  }
}
