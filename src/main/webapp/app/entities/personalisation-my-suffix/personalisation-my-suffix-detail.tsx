import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './personalisation-my-suffix.reducer';
import { IPersonalisationMySuffix } from 'app/shared/model/personalisation-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPersonalisationMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PersonalisationMySuffixDetail extends React.Component<IPersonalisationMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { personalisationEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="campaignToolApp.personalisation.detail.title">Personalisation</Translate> [<b>
              {personalisationEntity.id}
            </b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="customerNumber">
                <Translate contentKey="campaignToolApp.personalisation.customerNumber">Customer Number</Translate>
              </span>
            </dt>
            <dd>{personalisationEntity.customerNumber}</dd>
            <dt>
              <span id="userId">
                <Translate contentKey="campaignToolApp.personalisation.userId">User Id</Translate>
              </span>
            </dt>
            <dd>{personalisationEntity.userId}</dd>
            <dt>
              <span id="accountType">
                <Translate contentKey="campaignToolApp.personalisation.accountType">Account Type</Translate>
              </span>
            </dt>
            <dd>{personalisationEntity.accountType}</dd>
            <dt>
              <Translate contentKey="campaignToolApp.personalisation.campaign">Campaign</Translate>
            </dt>
            <dd>{personalisationEntity.campaignId ? personalisationEntity.campaignId : ''}</dd>
            <dt>
              <Translate contentKey="campaignToolApp.personalisation.manager">Manager</Translate>
            </dt>
            <dd>{personalisationEntity.managerId ? personalisationEntity.managerId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/personalisation-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/personalisation-my-suffix/${personalisationEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ personalisation }: IRootState) => ({
  personalisationEntity: personalisation.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PersonalisationMySuffixDetail);
