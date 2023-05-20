import { IDailyReport, NewDailyReport } from './daily-report.model';

export const sampleWithRequiredData: IDailyReport = {
  id: 12685,
};

export const sampleWithPartialData: IDailyReport = {
  id: 17423,
  succesfulTrx: 'Integration supply-chains',
};

export const sampleWithFullData: IDailyReport = {
  id: 3606,
  reportDate: 'plum',
  succesfulTrx: 'Principal Strategist panel',
  failedTrx: 'invoice',
  amount: 1893,
  configs: 'Rubber Centers Marketing',
  createdOn: 'Passage Dirham',
  createdBy: 'Metal',
  lastUpdatedOn: 'invoice',
  lastUpdatedBy: 'Chips turquoise',
};

export const sampleWithNewData: NewDailyReport = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
