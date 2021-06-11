import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './issue-system.reducer';
import { IIssueSystem } from 'app/shared/model/issue-system.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IIssueSystemUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const IssueSystemUpdate = (props: IIssueSystemUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { issueSystemEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/issue-system');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.iCreated = convertDateTimeToServer(values.iCreated);

    if (errors.length === 0) {
      const entity = {
        ...issueSystemEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="ebankv1App.issueSystem.home.createOrEditLabel" data-cy="IssueSystemCreateUpdateHeading">
            <Translate contentKey="ebankv1App.issueSystem.home.createOrEditLabel">Create or edit a IssueSystem</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : issueSystemEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="issue-system-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="issue-system-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="titleLabel" for="issue-system-title">
                  <Translate contentKey="ebankv1App.issueSystem.title">Title</Translate>
                </Label>
                <AvField id="issue-system-title" data-cy="title" type="text" name="title" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="issue-system-description">
                  <Translate contentKey="ebankv1App.issueSystem.description">Description</Translate>
                </Label>
                <AvField id="issue-system-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="iCreatedLabel" for="issue-system-iCreated">
                  <Translate contentKey="ebankv1App.issueSystem.iCreated">I Created</Translate>
                </Label>
                <AvInput
                  id="issue-system-iCreated"
                  data-cy="iCreated"
                  type="datetime-local"
                  className="form-control"
                  name="iCreated"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.issueSystemEntity.iCreated)}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/issue-system" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  issueSystemEntity: storeState.issueSystem.entity,
  loading: storeState.issueSystem.loading,
  updating: storeState.issueSystem.updating,
  updateSuccess: storeState.issueSystem.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(IssueSystemUpdate);
