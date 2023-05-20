import { IProject } from 'app/entities/project/project.model';

export interface IPayment {
  id: number;
  productId?: string | null;
  code?: string | null;
  subscriberId?: string | null;
  receiptId?: string | null;
  orderId?: string | null;
  phone?: string | null;
  amount?: string | null;
  currency?: string | null;
  paybill?: string | null;
  trxId?: string | null;
  trxEndTime?: string | null;
  trxStartTime?: string | null;
  status?: string | null;
  statusReason?: string | null;
  configs?: string | null;
  createdBy?: string | null;
  lastUpdatedOn?: string | null;
  lastUpdtedBy?: string | null;
  project?: Pick<IProject, 'id'> | null;
}

export type NewPayment = Omit<IPayment, 'id'> & { id: null };
