import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RecentTransaction from './recent-transaction';
import CustomerSupport from './customer-support';
import IssueSystem from './issue-system';
/* needle-add-route-import - will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}recent-transaction`} component={RecentTransaction} />
      <ErrorBoundaryRoute path={`${match.url}customer-support`} component={CustomerSupport} />
      <ErrorBoundaryRoute path={`${match.url}issue-system`} component={IssueSystem} /> 
      {/* needle-add-route-path -will add routes here */}
    </Switch>
  </div>
);

export default Routes;
