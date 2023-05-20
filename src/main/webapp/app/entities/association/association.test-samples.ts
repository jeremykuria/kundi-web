import { AssociationType } from 'app/entities/enumerations/association-type.model';

import { IAssociation, NewAssociation } from './association.model';

export const sampleWithRequiredData: IAssociation = {
  id: 96936,
};

export const sampleWithPartialData: IAssociation = {
  id: 35098,
  name: 'generate Burundi',
  brief: 'Towels',
  contentStatus: 'synergistic improvement',
  configs: 'Berkshire Integration Principal',
  createdBy: 'proactive Berkshire Loan',
  lastUpdatedOn: 'Towels',
  lastUpdatedBy: 'Gloves Rustic China',
};

export const sampleWithFullData: IAssociation = {
  id: 72339,
  name: 'info-mediaries sensor',
  brief: 'program dynamic',
  description: 'SQL',
  phoneNumber: 'Gorgeous Bike',
  contentStatus: 'matrices',
  paybill: 'incubate',
  associationType: AssociationType['UNIVERSITY'],
  configs: 'invoice circuit',
  createdBy: 'Granite Administrator',
  createdOn: 'Honduras',
  lastUpdatedOn: 'plum',
  lastUpdatedBy: 'Money',
};

export const sampleWithNewData: NewAssociation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
