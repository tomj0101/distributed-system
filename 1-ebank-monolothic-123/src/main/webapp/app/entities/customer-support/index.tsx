import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CustomerSupport from './customer-support';
import CustomerSupportDetail from './customer-support-detail';
import CustomerSupportUpdate from './customer-support-update';
import CustomerSupportDeleteDialog from './customer-support-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CustomerSupportUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CustomerSupportUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CustomerSupportDetail} />
      <ErrorBoundaryRoute path={match.url} component={CustomerSupport} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CustomerSupportDeleteDialog} />
  </>
);

export default Routes;
