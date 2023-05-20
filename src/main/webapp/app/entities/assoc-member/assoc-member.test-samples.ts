import { Role } from 'app/entities/enumerations/role.model';

import { IAssocMember, NewAssocMember } from './assoc-member.model';

export const sampleWithRequiredData: IAssocMember = {
  id: 55469,
};

export const sampleWithPartialData: IAssocMember = {
  id: 67695,
  email: 'Abdiel98@yahoo.com',
  lastUpdatedBy: 'Outdoors',
  lastUpdatedOn: 'New',
};

export const sampleWithFullData: IAssocMember = {
  id: 86792,
  name: 'Granite',
  email: 'Jarret.Franecki@hotmail.com',
  phone: '554.638.7147 x671',
  year: 'Kentucky Bacon',
  regNo: 'Account Automated',
  role: Role['ADMIN'],
  configs: 'Frozen Intelligent deposit',
  createdBy: 'Montserrat View Steel',
  createdOn: 'parsing Morocco Lanka',
  lastUpdatedBy: 'compressing Ruble',
  lastUpdatedOn: 'Pizza invoice Engineer',
};

export const sampleWithNewData: NewAssocMember = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
