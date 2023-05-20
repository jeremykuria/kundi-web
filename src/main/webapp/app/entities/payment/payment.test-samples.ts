import { IPayment, NewPayment } from './payment.model';

export const sampleWithRequiredData: IPayment = {
  id: 47537,
};

export const sampleWithPartialData: IPayment = {
  id: 13733,
  productId: 'Salad Hat',
  code: 'Engineer',
  orderId: 'Serbian generating',
  trxId: 'Account teal',
  trxEndTime: 'Technician Lek Brook',
  status: 'Corporate connect reintermediate',
  statusReason: 'Tasty',
  configs: 'hack',
  lastUpdatedOn: 'Sleek',
};

export const sampleWithFullData: IPayment = {
  id: 9515,
  productId: 'Shoes',
  code: 'digital',
  subscriberId: 'Card Synergized Solomon',
  receiptId: 'National',
  orderId: 'withdrawal Cambridgeshire',
  phone: '1-947-228-1339',
  amount: 'Ramp override drive',
  currency: 'plum Indiana',
  paybill: 'even-keeled',
  trxId: 'Specialist Money violet',
  trxEndTime: 'withdrawal experiences Rubber',
  trxStartTime: 'cutting-edge',
  status: 'archive',
  statusReason: 'generate systems',
  configs: 'engineer',
  createdBy: 'Salad',
  lastUpdatedOn: 'innovate array Plaza',
  lastUpdtedBy: 'IB Movies Account',
};

export const sampleWithNewData: NewPayment = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
