import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Status from './status';
import Address from './address';
import Product from './product';
import OrderMaster from './order-master';
import OrderDetails from './order-details';
/* needle-add-route-import - will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}status`} component={Status} />
      <ErrorBoundaryRoute path={`${match.url}address`} component={Address} />
      <ErrorBoundaryRoute path={`${match.url}product`} component={Product} />
      <ErrorBoundaryRoute path={`${match.url}order-master`} component={OrderMaster} />
      <ErrorBoundaryRoute path={`${match.url}order-details`} component={OrderDetails} />
      {/* needle-add-route-path - will add routes here */}
    </Switch>
  </div>
);

export default Routes;
