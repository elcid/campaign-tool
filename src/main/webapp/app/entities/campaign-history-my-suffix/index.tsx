import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CampaignHistoryMySuffix from './campaign-history-my-suffix';
import CampaignHistoryMySuffixDetail from './campaign-history-my-suffix-detail';
import CampaignHistoryMySuffixUpdate from './campaign-history-my-suffix-update';
import CampaignHistoryMySuffixDeleteDialog from './campaign-history-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CampaignHistoryMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CampaignHistoryMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CampaignHistoryMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={CampaignHistoryMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CampaignHistoryMySuffixDeleteDialog} />
  </>
);

export default Routes;
