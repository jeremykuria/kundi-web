import { Status } from 'app/entities/enumerations/status.model';
import { ReminderCycle } from 'app/entities/enumerations/reminder-cycle.model';

import { IPledge, NewPledge } from './pledge.model';

export const sampleWithRequiredData: IPledge = {
  id: 48693,
};

export const sampleWithPartialData: IPledge = {
  id: 72288,
  targetAmount: 58152,
  paidAmount: 15278,
  status: Status['FULFILLED'],
  noOfReminders: 22701,
  reminderCycle: ReminderCycle['WEEKLY'],
  configs: 'Highway quantify',
  createdBy: 'Liaison logistical Human',
};

export const sampleWithFullData: IPledge = {
  id: 25938,
  targetAmount: 99745,
  paidAmount: 75468,
  status: Status['ACTIVE'],
  noOfReminders: 80013,
  reminderCycle: ReminderCycle['WEEKLY'],
  configs: 'Reduced monitor',
  createdOn: 'system-worthy Investment',
  createdBy: 'Rupee',
  lastUpdatedOn: 'clicks-and-mortar global',
  lastUpdatedBy: 'solutions Sri SDD',
};

export const sampleWithNewData: NewPledge = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
