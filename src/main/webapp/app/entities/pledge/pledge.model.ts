import { IProject } from 'app/entities/project/project.model';
import { Status } from 'app/entities/enumerations/status.model';
import { ReminderCycle } from 'app/entities/enumerations/reminder-cycle.model';

export interface IPledge {
  id: number;
  targetAmount?: number | null;
  paidAmount?: number | null;
  status?: Status | null;
  noOfReminders?: number | null;
  reminderCycle?: ReminderCycle | null;
  configs?: string | null;
  createdOn?: string | null;
  createdBy?: string | null;
  lastUpdatedOn?: string | null;
  lastUpdatedBy?: string | null;
  project?: Pick<IProject, 'id'> | null;
}

export type NewPledge = Omit<IPledge, 'id'> & { id: null };
