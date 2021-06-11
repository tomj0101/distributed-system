import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import IssueSystem from './issue-system';
import IssueSystemDetail from './issue-system-detail';
import IssueSystemUpdate from './issue-system-update';
import IssueSystemDeleteDialog from './issue-system-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={IssueSystemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={IssueSystemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={IssueSystemDetail} />
      <ErrorBoundaryRoute path={match.url} component={IssueSystem} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={IssueSystemDeleteDialog} />
  </>
);

export default Routes;
