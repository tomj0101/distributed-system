import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './issue-system.reducer';
import { IIssueSystem } from 'app/shared/model/issue-system.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IIssueSystemProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const IssueSystem = (props: IIssueSystemProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { issueSystemList, match, loading } = props;
  return (
    <div>
      <h2 id="issue-system-heading" data-cy="IssueSystemHeading">
        <Translate contentKey="ebankv1App.issueSystem.home.title">Issue Systems</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="ebankv1App.issueSystem.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="ebankv1App.issueSystem.home.createLabel">Create new Issue System</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {issueSystemList && issueSystemList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="ebankv1App.issueSystem.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="ebankv1App.issueSystem.title">Title</Translate>
                </th>
                <th>
                  <Translate contentKey="ebankv1App.issueSystem.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="ebankv1App.issueSystem.iCreated">I Created</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {issueSystemList.map((issueSystem, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${issueSystem.id}`} color="link" size="sm">
                      {issueSystem.id}
                    </Button>
                  </td>
                  <td>{issueSystem.title}</td>
                  <td>{issueSystem.description}</td>
                  <td>{issueSystem.iCreated ? <TextFormat type="date" value={issueSystem.iCreated} format={APP_DATE_FORMAT} /> : null}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${issueSystem.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${issueSystem.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${issueSystem.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="ebankv1App.issueSystem.home.notFound">No Issue Systems found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ issueSystem }: IRootState) => ({
  issueSystemList: issueSystem.entities,
  loading: issueSystem.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(IssueSystem);
