export const enum AccountType {
  COMPACT = 'COMPACT',
  COMFORT = 'COMFORT',
  PREMIUM = 'PREMIUM'
}

export interface IPersonalisationMySuffix {
  id?: number;
  customerNumber?: number;
  userId?: number;
  accountType?: AccountType;
  campaignId?: number;
  managerId?: number;
}

export const defaultValue: Readonly<IPersonalisationMySuffix> = {};
