import { IAssociation } from 'app/entities/association/association.model';

export interface IProject {
  id: number;
  name?: string | null;
  targetAmount?: string | null;
  startDate?: string | null;
  endDate?: string | null;
  noOfPledgeReminder?: string | null;
  contributedAmount?: string | null;
  paybill?: string | null;
  configs?: string | null;
  createdBy?: string | null;
  createdOn?: string | null;
  lastUpdatedOn?: string | null;
  lastUpdatedBy?: string | null;
  association?: Pick<IAssociation, 'id'> | null;
}

export type NewProject = Omit<IProject, 'id'> & { id: null };
