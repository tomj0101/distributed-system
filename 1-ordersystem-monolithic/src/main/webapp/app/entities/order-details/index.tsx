import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OrderDetails from './order-details';
import OrderDetailsDetail from './order-details-detail';
import OrderDetailsUpdate from './order-details-update';
import OrderDetailsDeleteDialog from './order-details-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OrderDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OrderDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OrderDetailsDetail} />
      <ErrorBoundaryRoute path={match.url} component={OrderDetails} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={OrderDetailsDeleteDialog} />
  </>
);

export default Routes;
