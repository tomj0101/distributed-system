import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICustomerSupport, defaultValue } from 'app/shared/model/customer-support.model';

export const ACTION_TYPES = {
  FETCH_CUSTOMERSUPPORT_LIST: 'customerSupport/FETCH_CUSTOMERSUPPORT_LIST',
  FETCH_CUSTOMERSUPPORT: 'customerSupport/FETCH_CUSTOMERSUPPORT',
  CREATE_CUSTOMERSUPPORT: 'customerSupport/CREATE_CUSTOMERSUPPORT',
  UPDATE_CUSTOMERSUPPORT: 'customerSupport/UPDATE_CUSTOMERSUPPORT',
  PARTIAL_UPDATE_CUSTOMERSUPPORT: 'customerSupport/PARTIAL_UPDATE_CUSTOMERSUPPORT',
  DELETE_CUSTOMERSUPPORT: 'customerSupport/DELETE_CUSTOMERSUPPORT',
  RESET: 'customerSupport/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICustomerSupport>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CustomerSupportState = Readonly<typeof initialState>;

// Reducer

export default (state: CustomerSupportState = initialState, action): CustomerSupportState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CUSTOMERSUPPORT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CUSTOMERSUPPORT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CUSTOMERSUPPORT):
    case REQUEST(ACTION_TYPES.UPDATE_CUSTOMERSUPPORT):
    case REQUEST(ACTION_TYPES.DELETE_CUSTOMERSUPPORT):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_CUSTOMERSUPPORT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CUSTOMERSUPPORT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CUSTOMERSUPPORT):
    case FAILURE(ACTION_TYPES.CREATE_CUSTOMERSUPPORT):
    case FAILURE(ACTION_TYPES.UPDATE_CUSTOMERSUPPORT):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_CUSTOMERSUPPORT):
    case FAILURE(ACTION_TYPES.DELETE_CUSTOMERSUPPORT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CUSTOMERSUPPORT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CUSTOMERSUPPORT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CUSTOMERSUPPORT):
    case SUCCESS(ACTION_TYPES.UPDATE_CUSTOMERSUPPORT):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_CUSTOMERSUPPORT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CUSTOMERSUPPORT):
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

const apiUrl = 'api/customer-supports';

// Actions

export const getEntities: ICrudGetAllAction<ICustomerSupport> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CUSTOMERSUPPORT_LIST,
    payload: axios.get<ICustomerSupport>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICustomerSupport> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CUSTOMERSUPPORT,
    payload: axios.get<ICustomerSupport>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICustomerSupport> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CUSTOMERSUPPORT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICustomerSupport> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CUSTOMERSUPPORT,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICustomerSupport> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_CUSTOMERSUPPORT,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICustomerSupport> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CUSTOMERSUPPORT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
