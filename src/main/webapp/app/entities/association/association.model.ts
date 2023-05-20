import { IAssocMember } from 'app/entities/assoc-member/assoc-member.model';
import { AssociationType } from 'app/entities/enumerations/association-type.model';

export interface IAssociation {
  id: number;
  name?: string | null;
  brief?: string | null;
  description?: string | null;
  phoneNumber?: string | null;
  contentStatus?: string | null;
  paybill?: string | null;
  associationType?: AssociationType | null;
  configs?: string | null;
  createdBy?: string | null;
  createdOn?: string | null;
  lastUpdatedOn?: string | null;
  lastUpdatedBy?: string | null;
  members?: Pick<IAssocMember, 'id'>[] | null;
}

export type NewAssociation = Omit<IAssociation, 'id'> & { id: null };
