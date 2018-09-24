import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICampaignHistoryMySuffix, defaultValue } from 'app/shared/model/campaign-history-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_CAMPAIGNHISTORY_LIST: 'campaignHistory/FETCH_CAMPAIGNHISTORY_LIST',
  FETCH_CAMPAIGNHISTORY: 'campaignHistory/FETCH_CAMPAIGNHISTORY',
  CREATE_CAMPAIGNHISTORY: 'campaignHistory/CREATE_CAMPAIGNHISTORY',
  UPDATE_CAMPAIGNHISTORY: 'campaignHistory/UPDATE_CAMPAIGNHISTORY',
  DELETE_CAMPAIGNHISTORY: 'campaignHistory/DELETE_CAMPAIGNHISTORY',
  RESET: 'campaignHistory/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICampaignHistoryMySuffix>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type CampaignHistoryMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: CampaignHistoryMySuffixState = initialState, action): CampaignHistoryMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CAMPAIGNHISTORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CAMPAIGNHISTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CAMPAIGNHISTORY):
    case REQUEST(ACTION_TYPES.UPDATE_CAMPAIGNHISTORY):
    case REQUEST(ACTION_TYPES.DELETE_CAMPAIGNHISTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CAMPAIGNHISTORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CAMPAIGNHISTORY):
    case FAILURE(ACTION_TYPES.CREATE_CAMPAIGNHISTORY):
    case FAILURE(ACTION_TYPES.UPDATE_CAMPAIGNHISTORY):
    case FAILURE(ACTION_TYPES.DELETE_CAMPAIGNHISTORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CAMPAIGNHISTORY_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_CAMPAIGNHISTORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CAMPAIGNHISTORY):
    case SUCCESS(ACTION_TYPES.UPDATE_CAMPAIGNHISTORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CAMPAIGNHISTORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/campaign-histories';

// Actions

export const getEntities: ICrudGetAllAction<ICampaignHistoryMySuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CAMPAIGNHISTORY_LIST,
    payload: axios.get<ICampaignHistoryMySuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ICampaignHistoryMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CAMPAIGNHISTORY,
    payload: axios.get<ICampaignHistoryMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICampaignHistoryMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CAMPAIGNHISTORY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<ICampaignHistoryMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CAMPAIGNHISTORY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICampaignHistoryMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CAMPAIGNHISTORY,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
