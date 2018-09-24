import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IContentMySuffix, defaultValue } from 'app/shared/model/content-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_CONTENT_LIST: 'content/FETCH_CONTENT_LIST',
  FETCH_CONTENT: 'content/FETCH_CONTENT',
  CREATE_CONTENT: 'content/CREATE_CONTENT',
  UPDATE_CONTENT: 'content/UPDATE_CONTENT',
  DELETE_CONTENT: 'content/DELETE_CONTENT',
  RESET: 'content/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IContentMySuffix>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ContentMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: ContentMySuffixState = initialState, action): ContentMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CONTENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CONTENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CONTENT):
    case REQUEST(ACTION_TYPES.UPDATE_CONTENT):
    case REQUEST(ACTION_TYPES.DELETE_CONTENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CONTENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CONTENT):
    case FAILURE(ACTION_TYPES.CREATE_CONTENT):
    case FAILURE(ACTION_TYPES.UPDATE_CONTENT):
    case FAILURE(ACTION_TYPES.DELETE_CONTENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONTENT_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONTENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CONTENT):
    case SUCCESS(ACTION_TYPES.UPDATE_CONTENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CONTENT):
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

const apiUrl = 'api/contents';

// Actions

export const getEntities: ICrudGetAllAction<IContentMySuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CONTENT_LIST,
    payload: axios.get<IContentMySuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IContentMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CONTENT,
    payload: axios.get<IContentMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IContentMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CONTENT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IContentMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CONTENT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IContentMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CONTENT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
