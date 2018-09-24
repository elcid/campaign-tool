import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICampaignMySuffix } from 'app/shared/model/campaign-my-suffix.model';
import { getEntities as getCampaigns } from 'app/entities/campaign-my-suffix/campaign-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, reset } from './campaign-history-my-suffix.reducer';
import { ICampaignHistoryMySuffix } from 'app/shared/model/campaign-history-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICampaignHistoryMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICampaignHistoryMySuffixUpdateState {
  isNew: boolean;
  campaignId: string;
}

export class CampaignHistoryMySuffixUpdate extends React.Component<
  ICampaignHistoryMySuffixUpdateProps,
  ICampaignHistoryMySuffixUpdateState
> {
  constructor(props) {
    super(props);
    this.state = {
      campaignId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getCampaigns();
  }

  saveEntity = (event, errors, values) => {
    values.startDate = new Date(values.startDate);
    values.endDate = new Date(values.endDate);

    if (errors.length === 0) {
      const { campaignHistoryEntity } = this.props;
      const entity = {
        ...campaignHistoryEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
      this.handleClose();
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/campaign-history-my-suffix');
  };

  render() {
    const { campaignHistoryEntity, campaigns, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="campaignToolApp.campaignHistory.home.createOrEditLabel">
              <Translate contentKey="campaignToolApp.campaignHistory.home.createOrEditLabel">Create or edit a CampaignHistory</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : campaignHistoryEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="campaign-history-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="startDateLabel" for="startDate">
                    <Translate contentKey="campaignToolApp.campaignHistory.startDate">Start Date</Translate>
                  </Label>
                  <AvInput
                    id="campaign-history-my-suffix-startDate"
                    type="datetime-local"
                    className="form-control"
                    name="startDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.campaignHistoryEntity.startDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="endDateLabel" for="endDate">
                    <Translate contentKey="campaignToolApp.campaignHistory.endDate">End Date</Translate>
                  </Label>
                  <AvInput
                    id="campaign-history-my-suffix-endDate"
                    type="datetime-local"
                    className="form-control"
                    name="endDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.campaignHistoryEntity.endDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="campaign.id">
                    <Translate contentKey="campaignToolApp.campaignHistory.campaign">Campaign</Translate>
                  </Label>
                  <AvInput id="campaign-history-my-suffix-campaign" type="select" className="form-control" name="campaignId">
                    <option value="" key="0" />
                    {campaigns
                      ? campaigns.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/campaign-history-my-suffix" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  campaigns: storeState.campaign.entities,
  campaignHistoryEntity: storeState.campaignHistory.entity,
  loading: storeState.campaignHistory.loading,
  updating: storeState.campaignHistory.updating
});

const mapDispatchToProps = {
  getCampaigns,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CampaignHistoryMySuffixUpdate);
