import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IIssueSystem, defaultValue } from 'app/shared/model/issue-system.model';

export const ACTION_TYPES = {
  FETCH_ISSUESYSTEM_LIST: 'issueSystem/FETCH_ISSUESYSTEM_LIST',
  FETCH_ISSUESYSTEM: 'issueSystem/FETCH_ISSUESYSTEM',
  CREATE_ISSUESYSTEM: 'issueSystem/CREATE_ISSUESYSTEM',
  UPDATE_ISSUESYSTEM: 'issueSystem/UPDATE_ISSUESYSTEM',
  PARTIAL_UPDATE_ISSUESYSTEM: 'issueSystem/PARTIAL_UPDATE_ISSUESYSTEM',
  DELETE_ISSUESYSTEM: 'issueSystem/DELETE_ISSUESYSTEM',
  RESET: 'issueSystem/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IIssueSystem>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type IssueSystemState = Readonly<typeof initialState>;

// Reducer

export default (state: IssueSystemState = initialState, action): IssueSystemState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ISSUESYSTEM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ISSUESYSTEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ISSUESYSTEM):
    case REQUEST(ACTION_TYPES.UPDATE_ISSUESYSTEM):
    case REQUEST(ACTION_TYPES.DELETE_ISSUESYSTEM):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ISSUESYSTEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ISSUESYSTEM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ISSUESYSTEM):
    case FAILURE(ACTION_TYPES.CREATE_ISSUESYSTEM):
    case FAILURE(ACTION_TYPES.UPDATE_ISSUESYSTEM):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ISSUESYSTEM):
    case FAILURE(ACTION_TYPES.DELETE_ISSUESYSTEM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ISSUESYSTEM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ISSUESYSTEM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ISSUESYSTEM):
    case SUCCESS(ACTION_TYPES.UPDATE_ISSUESYSTEM):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ISSUESYSTEM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ISSUESYSTEM):
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

const apiUrl = 'api/issue-systems';

// Actions

export const getEntities: ICrudGetAllAction<IIssueSystem> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ISSUESYSTEM_LIST,
  payload: axios.get<IIssueSystem>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IIssueSystem> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ISSUESYSTEM,
    payload: axios.get<IIssueSystem>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IIssueSystem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ISSUESYSTEM,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IIssueSystem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ISSUESYSTEM,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IIssueSystem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ISSUESYSTEM,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IIssueSystem> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ISSUESYSTEM,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
