import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './personalisation-my-suffix.reducer';
import { IPersonalisationMySuffix } from 'app/shared/model/personalisation-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPersonalisationMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class PersonalisationMySuffix extends React.Component<IPersonalisationMySuffixProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { personalisationList, match } = this.props;
    return (
      <div>
        <h2 id="personalisation-my-suffix-heading">
          <Translate contentKey="campaignToolApp.personalisation.home.title">Personalisations</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp;
            <Translate contentKey="campaignToolApp.personalisation.home.createLabel">Create new Personalisation</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="campaignToolApp.personalisation.customerNumber">Customer Number</Translate>
                </th>
                <th>
                  <Translate contentKey="campaignToolApp.personalisation.userId">User Id</Translate>
                </th>
                <th>
                  <Translate contentKey="campaignToolApp.personalisation.accountType">Account Type</Translate>
                </th>
                <th>
                  <Translate contentKey="campaignToolApp.personalisation.campaign">Campaign</Translate>
                </th>
                <th>
                  <Translate contentKey="campaignToolApp.personalisation.manager">Manager</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {personalisationList.map((personalisation, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${personalisation.id}`} color="link" size="sm">
                      {personalisation.id}
                    </Button>
                  </td>
                  <td>{personalisation.customerNumber}</td>
                  <td>{personalisation.userId}</td>
                  <td>
                    <Translate contentKey={`campaignToolApp.AccountType.${personalisation.accountType}`} />
                  </td>
                  <td>
                    {personalisation.campaignId ? (
                      <Link to={`campaign-my-suffix/${personalisation.campaignId}`}>{personalisation.campaignId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {personalisation.managerId ? (
                      <Link to={`campaign-my-suffix/${personalisation.managerId}`}>{personalisation.managerId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${personalisation.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${personalisation.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${personalisation.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ personalisation }: IRootState) => ({
  personalisationList: personalisation.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PersonalisationMySuffix);
