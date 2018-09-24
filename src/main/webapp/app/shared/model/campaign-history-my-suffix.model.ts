import { Moment } from 'moment';

export interface ICampaignHistoryMySuffix {
  id?: number;
  startDate?: Moment;
  endDate?: Moment;
  campaignId?: number;
}

export const defaultValue: Readonly<ICampaignHistoryMySuffix> = {};
