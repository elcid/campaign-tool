import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ContentMySuffix from './content-my-suffix';
import ContentMySuffixDetail from './content-my-suffix-detail';
import ContentMySuffixUpdate from './content-my-suffix-update';
import ContentMySuffixDeleteDialog from './content-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ContentMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ContentMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ContentMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={ContentMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ContentMySuffixDeleteDialog} />
  </>
);

export default Routes;
