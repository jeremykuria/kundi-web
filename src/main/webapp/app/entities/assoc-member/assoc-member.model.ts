import { IPledge } from 'app/entities/pledge/pledge.model';
import { IAssociation } from 'app/entities/association/association.model';
import { Role } from 'app/entities/enumerations/role.model';

export interface IAssocMember {
  id: number;
  name?: string | null;
  email?: string | null;
  phone?: string | null;
  year?: string | null;
  regNo?: string | null;
  role?: Role | null;
  configs?: string | null;
  createdBy?: string | null;
  createdOn?: string | null;
  lastUpdatedBy?: string | null;
  lastUpdatedOn?: string | null;
  pledge?: Pick<IPledge, 'id'> | null;
  associations?: Pick<IAssociation, 'id'>[] | null;
}

export type NewAssocMember = Omit<IAssocMember, 'id'> & { id: null };
