import { Status } from 'app/entities/enumerations/status.model';
import { NotificationType } from 'app/entities/enumerations/notification-type.model';

import { INotification, NewNotification } from './notification.model';

export const sampleWithRequiredData: INotification = {
  id: 30621,
};

export const sampleWithPartialData: INotification = {
  id: 16360,
  cost: 'Customizable synergies',
  email: 'Nicole19@yahoo.com',
  msisdn: 'card User generate',
  notificationType: NotificationType['SMS'],
};

export const sampleWithFullData: INotification = {
  id: 79381,
  status: Status['ACTIVE'],
  content: 'Web next-generation Oro',
  cost: 'Mouse pink',
  email: 'Katharina32@gmail.com',
  msisdn: 'Representative cyan',
  notificationType: NotificationType['EMAIL'],
  configs: 'Computers',
  createdOn: 'Course parse',
  createdBy: 'incentivize',
  lastUpdatedOn: 'JSON Public-key invoice',
  lastUpdatedBy: 'Solutions calculating Keyboard',
};

export const sampleWithNewData: NewNotification = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
