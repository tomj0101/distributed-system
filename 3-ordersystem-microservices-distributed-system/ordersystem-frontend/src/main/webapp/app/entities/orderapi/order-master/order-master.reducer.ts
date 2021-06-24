import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOrderMaster, defaultValue } from 'app/shared/model/orderapi/order-master.model';

export const ACTION_TYPES = {
  SEARCH_ORDERMASTERS: 'orderMaster/SEARCH_ORDERMASTERS',
  FETCH_ORDERMASTER_LIST: 'orderMaster/FETCH_ORDERMASTER_LIST',
  FETCH_ORDERMASTER: 'orderMaster/FETCH_ORDERMASTER',
  CREATE_ORDERMASTER: 'orderMaster/CREATE_ORDERMASTER',
  UPDATE_ORDERMASTER: 'orderMaster/UPDATE_ORDERMASTER',
  PARTIAL_UPDATE_ORDERMASTER: 'orderMaster/PARTIAL_UPDATE_ORDERMASTER',
  DELETE_ORDERMASTER: 'orderMaster/DELETE_ORDERMASTER',
  RESET: 'orderMaster/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOrderMaster>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type OrderMasterState = Readonly<typeof initialState>;

// Reducer

export default (state: OrderMasterState = initialState, action): OrderMasterState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_ORDERMASTERS):
    case REQUEST(ACTION_TYPES.FETCH_ORDERMASTER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ORDERMASTER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ORDERMASTER):
    case REQUEST(ACTION_TYPES.UPDATE_ORDERMASTER):
    case REQUEST(ACTION_TYPES.DELETE_ORDERMASTER):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ORDERMASTER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_ORDERMASTERS):
    case FAILURE(ACTION_TYPES.FETCH_ORDERMASTER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ORDERMASTER):
    case FAILURE(ACTION_TYPES.CREATE_ORDERMASTER):
    case FAILURE(ACTION_TYPES.UPDATE_ORDERMASTER):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ORDERMASTER):
    case FAILURE(ACTION_TYPES.DELETE_ORDERMASTER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_ORDERMASTERS):
    case SUCCESS(ACTION_TYPES.FETCH_ORDERMASTER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORDERMASTER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ORDERMASTER):
    case SUCCESS(ACTION_TYPES.UPDATE_ORDERMASTER):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ORDERMASTER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ORDERMASTER):
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

const apiUrl = 'services/orderapi/api/order-masters';
const apiSearchUrl = 'services/orderapi/api/_search/order-masters';

// Actions

export const getSearchEntities: ICrudSearchAction<IOrderMaster> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_ORDERMASTERS,
  payload: axios.get<IOrderMaster>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IOrderMaster> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ORDERMASTER_LIST,
    payload: axios.get<IOrderMaster>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IOrderMaster> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ORDERMASTER,
    payload: axios.get<IOrderMaster>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IOrderMaster> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ORDERMASTER,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOrderMaster> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ORDERMASTER,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IOrderMaster> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ORDERMASTER,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOrderMaster> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ORDERMASTER,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
