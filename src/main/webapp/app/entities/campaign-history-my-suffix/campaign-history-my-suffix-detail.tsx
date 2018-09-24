import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './campaign-history-my-suffix.reducer';
import { ICampaignHistoryMySuffix } from 'app/shared/model/campaign-history-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICampaignHistoryMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CampaignHistoryMySuffixDetail extends React.Component<ICampaignHistoryMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { campaignHistoryEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="campaignToolApp.campaignHistory.detail.title">CampaignHistory</Translate> [<b>
              {campaignHistoryEntity.id}
            </b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="startDate">
                <Translate contentKey="campaignToolApp.campaignHistory.startDate">Start Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={campaignHistoryEntity.startDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="endDate">
                <Translate contentKey="campaignToolApp.campaignHistory.endDate">End Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={campaignHistoryEntity.endDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="campaignToolApp.campaignHistory.campaign">Campaign</Translate>
            </dt>
            <dd>{campaignHistoryEntity.campaignId ? campaignHistoryEntity.campaignId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/campaign-history-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/campaign-history-my-suffix/${campaignHistoryEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ campaignHistory }: IRootState) => ({
  campaignHistoryEntity: campaignHistory.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CampaignHistoryMySuffixDetail);
