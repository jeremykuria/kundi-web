export interface IDailyReport {
  id: number;
  reportDate?: string | null;
  succesfulTrx?: string | null;
  failedTrx?: string | null;
  amount?: number | null;
  configs?: string | null;
  createdOn?: string | null;
  createdBy?: string | null;
  lastUpdatedOn?: string | null;
  lastUpdatedBy?: string | null;
}

export type NewDailyReport = Omit<IDailyReport, 'id'> & { id: null };
