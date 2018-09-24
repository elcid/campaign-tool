import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CampaignMySuffix from './campaign-my-suffix';
import CampaignMySuffixDetail from './campaign-my-suffix-detail';
import CampaignMySuffixUpdate from './campaign-my-suffix-update';
import CampaignMySuffixDeleteDialog from './campaign-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CampaignMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CampaignMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CampaignMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={CampaignMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CampaignMySuffixDeleteDialog} />
  </>
);

export default Routes;
