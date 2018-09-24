import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PersonalisationMySuffix from './personalisation-my-suffix';
import PersonalisationMySuffixDetail from './personalisation-my-suffix-detail';
import PersonalisationMySuffixUpdate from './personalisation-my-suffix-update';
import PersonalisationMySuffixDeleteDialog from './personalisation-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PersonalisationMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PersonalisationMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PersonalisationMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={PersonalisationMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PersonalisationMySuffixDeleteDialog} />
  </>
);

export default Routes;
