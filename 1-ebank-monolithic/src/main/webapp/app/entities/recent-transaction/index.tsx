import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RecentTransaction from './recent-transaction';
import RecentTransactionDetail from './recent-transaction-detail';
import RecentTransactionUpdate from './recent-transaction-update';
import RecentTransactionDeleteDialog from './recent-transaction-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RecentTransactionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RecentTransactionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RecentTransactionDetail} />
      <ErrorBoundaryRoute path={match.url} component={RecentTransaction} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RecentTransactionDeleteDialog} />
  </>
);

export default Routes;
