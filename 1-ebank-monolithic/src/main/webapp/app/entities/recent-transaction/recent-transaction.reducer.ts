import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRecentTransaction, defaultValue } from 'app/shared/model/recent-transaction.model';

export const ACTION_TYPES = {
  FETCH_RECENTTRANSACTION_LIST: 'recentTransaction/FETCH_RECENTTRANSACTION_LIST',
  FETCH_RECENTTRANSACTION: 'recentTransaction/FETCH_RECENTTRANSACTION',
  CREATE_RECENTTRANSACTION: 'recentTransaction/CREATE_RECENTTRANSACTION',
  UPDATE_RECENTTRANSACTION: 'recentTransaction/UPDATE_RECENTTRANSACTION',
  PARTIAL_UPDATE_RECENTTRANSACTION: 'recentTransaction/PARTIAL_UPDATE_RECENTTRANSACTION',
  DELETE_RECENTTRANSACTION: 'recentTransaction/DELETE_RECENTTRANSACTION',
  RESET: 'recentTransaction/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRecentTransaction>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type RecentTransactionState = Readonly<typeof initialState>;

// Reducer

export default (state: RecentTransactionState = initialState, action): RecentTransactionState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RECENTTRANSACTION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RECENTTRANSACTION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_RECENTTRANSACTION):
    case REQUEST(ACTION_TYPES.UPDATE_RECENTTRANSACTION):
    case REQUEST(ACTION_TYPES.DELETE_RECENTTRANSACTION):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_RECENTTRANSACTION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_RECENTTRANSACTION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RECENTTRANSACTION):
    case FAILURE(ACTION_TYPES.CREATE_RECENTTRANSACTION):
    case FAILURE(ACTION_TYPES.UPDATE_RECENTTRANSACTION):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_RECENTTRANSACTION):
    case FAILURE(ACTION_TYPES.DELETE_RECENTTRANSACTION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RECENTTRANSACTION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_RECENTTRANSACTION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_RECENTTRANSACTION):
    case SUCCESS(ACTION_TYPES.UPDATE_RECENTTRANSACTION):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_RECENTTRANSACTION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_RECENTTRANSACTION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/recent-transactions';

// Actions

export const getEntities: ICrudGetAllAction<IRecentTransaction> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_RECENTTRANSACTION_LIST,
    payload: axios.get<IRecentTransaction>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IRecentTransaction> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RECENTTRANSACTION,
    payload: axios.get<IRecentTransaction>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IRecentTransaction> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RECENTTRANSACTION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRecentTransaction> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RECENTTRANSACTION,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IRecentTransaction> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_RECENTTRANSACTION,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRecentTransaction> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RECENTTRANSACTION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
