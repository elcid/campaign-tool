import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPersonalisationMySuffix, defaultValue } from 'app/shared/model/personalisation-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_PERSONALISATION_LIST: 'personalisation/FETCH_PERSONALISATION_LIST',
  FETCH_PERSONALISATION: 'personalisation/FETCH_PERSONALISATION',
  CREATE_PERSONALISATION: 'personalisation/CREATE_PERSONALISATION',
  UPDATE_PERSONALISATION: 'personalisation/UPDATE_PERSONALISATION',
  DELETE_PERSONALISATION: 'personalisation/DELETE_PERSONALISATION',
  RESET: 'personalisation/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPersonalisationMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PersonalisationMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: PersonalisationMySuffixState = initialState, action): PersonalisationMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PERSONALISATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PERSONALISATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PERSONALISATION):
    case REQUEST(ACTION_TYPES.UPDATE_PERSONALISATION):
    case REQUEST(ACTION_TYPES.DELETE_PERSONALISATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PERSONALISATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PERSONALISATION):
    case FAILURE(ACTION_TYPES.CREATE_PERSONALISATION):
    case FAILURE(ACTION_TYPES.UPDATE_PERSONALISATION):
    case FAILURE(ACTION_TYPES.DELETE_PERSONALISATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PERSONALISATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PERSONALISATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PERSONALISATION):
    case SUCCESS(ACTION_TYPES.UPDATE_PERSONALISATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PERSONALISATION):
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

const apiUrl = 'api/personalisations';

// Actions

export const getEntities: ICrudGetAllAction<IPersonalisationMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PERSONALISATION_LIST,
  payload: axios.get<IPersonalisationMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPersonalisationMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PERSONALISATION,
    payload: axios.get<IPersonalisationMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPersonalisationMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PERSONALISATION,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPersonalisationMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PERSONALISATION,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPersonalisationMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PERSONALISATION,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
