import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IContentMySuffix } from 'app/shared/model/content-my-suffix.model';
import { getEntities as getContents } from 'app/entities/content-my-suffix/content-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, reset } from './campaign-my-suffix.reducer';
import { ICampaignMySuffix } from 'app/shared/model/campaign-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICampaignMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICampaignMySuffixUpdateState {
  isNew: boolean;
  contentId: string;
}

export class CampaignMySuffixUpdate extends React.Component<ICampaignMySuffixUpdateProps, ICampaignMySuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      contentId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getContents();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { campaignEntity } = this.props;
      const entity = {
        ...campaignEntity,
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
    this.props.history.push('/entity/campaign-my-suffix');
  };

  render() {
    const { campaignEntity, contents, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="campaignToolApp.campaign.home.createOrEditLabel">
              <Translate contentKey="campaignToolApp.campaign.home.createOrEditLabel">Create or edit a Campaign</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : campaignEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="campaign-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="titleLabel" for="title">
                    <Translate contentKey="campaignToolApp.campaign.title">Title</Translate>
                  </Label>
                  <AvField
                    id="campaign-my-suffix-title"
                    type="text"
                    name="title"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="description">
                    <Translate contentKey="campaignToolApp.campaign.description">Description</Translate>
                  </Label>
                  <AvField id="campaign-my-suffix-description" type="text" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label id="startDateLabel" for="startDate">
                    <Translate contentKey="campaignToolApp.campaign.startDate">Start Date</Translate>
                  </Label>
                  <AvField id="campaign-my-suffix-startDate" type="date" className="form-control" name="startDate" />
                </AvGroup>
                <AvGroup>
                  <Label id="endDateLabel" for="endDate">
                    <Translate contentKey="campaignToolApp.campaign.endDate">End Date</Translate>
                  </Label>
                  <AvField id="campaign-my-suffix-endDate" type="date" className="form-control" name="endDate" />
                </AvGroup>
                <AvGroup>
                  <Label for="content.id">
                    <Translate contentKey="campaignToolApp.campaign.content">Content</Translate>
                  </Label>
                  <AvInput id="campaign-my-suffix-content" type="select" className="form-control" name="contentId">
                    <option value="" key="0" />
                    {contents
                      ? contents.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/campaign-my-suffix" replace color="info">
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
  contents: storeState.content.entities,
  campaignEntity: storeState.campaign.entity,
  loading: storeState.campaign.loading,
  updating: storeState.campaign.updating
});

const mapDispatchToProps = {
  getContents,
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
)(CampaignMySuffixUpdate);
