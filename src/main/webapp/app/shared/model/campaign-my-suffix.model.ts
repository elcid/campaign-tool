import { Moment } from 'moment';
import { IPersonalisationMySuffix } from 'app/shared/model//personalisation-my-suffix.model';

export interface ICampaignMySuffix {
  id?: number;
  title?: string;
  description?: string;
  startDate?: Moment;
  endDate?: Moment;
  contentId?: number;
  personalisations?: IPersonalisationMySuffix[];
}

export const defaultValue: Readonly<ICampaignMySuffix> = {};
