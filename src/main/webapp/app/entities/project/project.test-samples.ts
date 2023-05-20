import { IProject, NewProject } from './project.model';

export const sampleWithRequiredData: IProject = {
  id: 55962,
};

export const sampleWithPartialData: IProject = {
  id: 53955,
  name: 'users parse AGP',
  targetAmount: 'dot-com',
  startDate: 'Health capacitor',
  endDate: 'Investor Loan Avon',
  configs: 'secondary users invoice',
  createdBy: 'strategize salmon olive',
  createdOn: 'applications Enterprise-wide Sausages',
  lastUpdatedOn: 'state USB capability',
};

export const sampleWithFullData: IProject = {
  id: 34684,
  name: 'Cotton Cedi',
  targetAmount: 'Granite EXE solutions',
  startDate: 'Developer schemas',
  endDate: 'Auto leverage Computers',
  noOfPledgeReminder: 'Cambridgeshire',
  contributedAmount: 'Communications Electronics',
  paybill: 'Unbranded Kyrgyz purple',
  configs: 'SCSI Mouse',
  createdBy: 'azure',
  createdOn: 'Cotton Specialist',
  lastUpdatedOn: 'Guinea ADP product',
  lastUpdatedBy: 'system benchmark Hat',
};

export const sampleWithNewData: NewProject = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
