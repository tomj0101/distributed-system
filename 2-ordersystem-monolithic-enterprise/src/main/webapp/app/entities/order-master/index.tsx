import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OrderMaster from './order-master';
import OrderMasterDetail from './order-master-detail';
import OrderMasterUpdate from './order-master-update';
import OrderMasterDeleteDialog from './order-master-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OrderMasterUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OrderMasterUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OrderMasterDetail} />
      <ErrorBoundaryRoute path={match.url} component={OrderMaster} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={OrderMasterDeleteDialog} />
  </>
);

export default Routes;
