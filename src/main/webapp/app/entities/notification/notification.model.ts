import { IAssociation } from 'app/entities/association/association.model';
import { Status } from 'app/entities/enumerations/status.model';
import { NotificationType } from 'app/entities/enumerations/notification-type.model';

export interface INotification {
  id: number;
  status?: Status | null;
  content?: string | null;
  cost?: string | null;
  email?: string | null;
  msisdn?: string | null;
  notificationType?: NotificationType | null;
  configs?: string | null;
  createdOn?: string | null;
  createdBy?: string | null;
  lastUpdatedOn?: string | null;
  lastUpdatedBy?: string | null;
  association?: Pick<IAssociation, 'id'> | null;
}

export type NewNotification = Omit<INotification, 'id'> & { id: null };
