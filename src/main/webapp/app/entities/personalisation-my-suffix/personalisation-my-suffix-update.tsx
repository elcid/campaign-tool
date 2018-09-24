import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICampaignMySuffix } from 'app/shared/model/campaign-my-suffix.model';
import { getEntities as getCampaigns } from 'app/entities/campaign-my-suffix/campaign-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, reset } from './personalisation-my-suffix.reducer';
import { IPersonalisationMySuffix } from 'app/shared/model/personalisation-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPersonalisationMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPersonalisationMySuffixUpdateState {
  isNew: boolean;
  campaignId: string;
  managerId: string;
}

export class PersonalisationMySuffixUpdate extends React.Component<
  IPersonalisationMySuffixUpdateProps,
  IPersonalisationMySuffixUpdateState
> {
  constructor(props) {
    super(props);
    this.state = {
      campaignId: '0',
      managerId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getCampaigns();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { personalisationEntity } = this.props;
      const entity = {
        ...personalisationEntity,
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
    this.props.history.push('/entity/personalisation-my-suffix');
  };

  render() {
    const { personalisationEntity, campaigns, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="campaignToolApp.personalisation.home.createOrEditLabel">
              <Translate contentKey="campaignToolApp.personalisation.home.createOrEditLabel">Create or edit a Personalisation</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : personalisationEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="personalisation-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="customerNumberLabel" for="customerNumber">
                    <Translate contentKey="campaignToolApp.personalisation.customerNumber">Customer Number</Translate>
                  </Label>
                  <AvField id="personalisation-my-suffix-customerNumber" type="string" className="form-control" name="customerNumber" />
                </AvGroup>
                <AvGroup>
                  <Label id="userIdLabel" for="userId">
                    <Translate contentKey="campaignToolApp.personalisation.userId">User Id</Translate>
                  </Label>
                  <AvField id="personalisation-my-suffix-userId" type="string" className="form-control" name="userId" />
                </AvGroup>
                <AvGroup>
                  <Label id="accountTypeLabel">
                    <Translate contentKey="campaignToolApp.personalisation.accountType">Account Type</Translate>
                  </Label>
                  <AvInput
                    id="personalisation-my-suffix-accountType"
                    type="select"
                    className="form-control"
                    name="accountType"
                    value={(!isNew && personalisationEntity.accountType) || 'COMPACT'}
                  >
                    <option value="COMPACT">
                      <Translate contentKey="campaignToolApp.AccountType.COMPACT" />
                    </option>
                    <option value="COMFORT">
                      <Translate contentKey="campaignToolApp.AccountType.COMFORT" />
                    </option>
                    <option value="PREMIUM">
                      <Translate contentKey="campaignToolApp.AccountType.PREMIUM" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="campaign.id">
                    <Translate contentKey="campaignToolApp.personalisation.campaign">Campaign</Translate>
                  </Label>
                  <AvInput id="personalisation-my-suffix-campaign" type="select" className="form-control" name="campaignId">
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
                <AvGroup>
                  <Label for="manager.id">
                    <Translate contentKey="campaignToolApp.personalisation.manager">Manager</Translate>
                  </Label>
                  <AvInput id="personalisation-my-suffix-manager" type="select" className="form-control" name="managerId">
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
                <Button tag={Link} id="cancel-save" to="/entity/personalisation-my-suffix" replace color="info">
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
  personalisationEntity: storeState.personalisation.entity,
  loading: storeState.personalisation.loading,
  updating: storeState.personalisation.updating
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
)(PersonalisationMySuffixUpdate);
