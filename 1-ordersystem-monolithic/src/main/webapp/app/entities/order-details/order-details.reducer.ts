import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction,
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOrderDetails, defaultValue } from 'app/shared/model/order-details.model';

export const ACTION_TYPES = {
  FETCH_ORDERDETAILS_LIST: 'orderDetails/FETCH_ORDERDETAILS_LIST',
  FETCH_ORDERDETAILS: 'orderDetails/FETCH_ORDERDETAILS',
  CREATE_ORDERDETAILS: 'orderDetails/CREATE_ORDERDETAILS',
  UPDATE_ORDERDETAILS: 'orderDetails/UPDATE_ORDERDETAILS',
  PARTIAL_UPDATE_ORDERDETAILS: 'orderDetails/PARTIAL_UPDATE_ORDERDETAILS',
  DELETE_ORDERDETAILS: 'orderDetails/DELETE_ORDERDETAILS',
  RESET: 'orderDetails/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOrderDetails>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type OrderDetailsState = Readonly<typeof initialState>;

// Reducer

export default (state: OrderDetailsState = initialState, action): OrderDetailsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ORDERDETAILS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ORDERDETAILS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ORDERDETAILS):
    case REQUEST(ACTION_TYPES.UPDATE_ORDERDETAILS):
    case REQUEST(ACTION_TYPES.DELETE_ORDERDETAILS):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ORDERDETAILS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ORDERDETAILS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ORDERDETAILS):
    case FAILURE(ACTION_TYPES.CREATE_ORDERDETAILS):
    case FAILURE(ACTION_TYPES.UPDATE_ORDERDETAILS):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ORDERDETAILS):
    case FAILURE(ACTION_TYPES.DELETE_ORDERDETAILS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORDERDETAILS_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_ORDERDETAILS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ORDERDETAILS):
    case SUCCESS(ACTION_TYPES.UPDATE_ORDERDETAILS):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ORDERDETAILS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ORDERDETAILS):
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

const apiUrl = 'api/order-details';

// Actions

export const getEntities: ICrudGetAllAction<IOrderDetails> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ORDERDETAILS_LIST,
    payload: axios.get<IOrderDetails>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IOrderDetails> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ORDERDETAILS,
    payload: axios.get<IOrderDetails>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IOrderDetails> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ORDERDETAILS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IOrderDetails> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ORDERDETAILS,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IOrderDetails> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ORDERDETAILS,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOrderDetails> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ORDERDETAILS,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
