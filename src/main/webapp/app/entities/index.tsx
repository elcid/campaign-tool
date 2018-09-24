import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CampaignMySuffix from './campaign-my-suffix';
import PersonalisationMySuffix from './personalisation-my-suffix';
import ContentMySuffix from './content-my-suffix';
import CampaignHistoryMySuffix from './campaign-history-my-suffix';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/campaign-my-suffix`} component={CampaignMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/personalisation-my-suffix`} component={PersonalisationMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/content-my-suffix`} component={ContentMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/campaign-history-my-suffix`} component={CampaignHistoryMySuffix} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
