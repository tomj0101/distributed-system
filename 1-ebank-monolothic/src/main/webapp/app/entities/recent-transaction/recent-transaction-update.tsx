import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './recent-transaction.reducer';
import { IRecentTransaction } from 'app/shared/model/recent-transaction.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRecentTransactionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RecentTransactionUpdate = (props: IRecentTransactionUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { recentTransactionEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/recent-transaction' + props.location.search);
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
    values.tCreated = convertDateTimeToServer(values.tCreated);

    if (errors.length === 0) {
      const entity = {
        ...recentTransactionEntity,
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
          <h2 id="ebankv1App.recentTransaction.home.createOrEditLabel" data-cy="RecentTransactionCreateUpdateHeading">
            <Translate contentKey="ebankv1App.recentTransaction.home.createOrEditLabel">Create or edit a RecentTransaction</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : recentTransactionEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="recent-transaction-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="recent-transaction-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descriptionLabel" for="recent-transaction-description">
                  <Translate contentKey="ebankv1App.recentTransaction.description">Description</Translate>
                </Label>
                <AvField
                  id="recent-transaction-description"
                  data-cy="description"
                  type="text"
                  name="description"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    minLength: { value: 3, errorMessage: translate('entity.validation.minlength', { min: 3 }) },
                    maxLength: { value: 50, errorMessage: translate('entity.validation.maxlength', { max: 50 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="amountLabel" for="recent-transaction-amount">
                  <Translate contentKey="ebankv1App.recentTransaction.amount">Amount</Translate>
                </Label>
                <AvField
                  id="recent-transaction-amount"
                  data-cy="amount"
                  type="string"
                  className="form-control"
                  name="amount"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="recent-transaction-status">
                  <Translate contentKey="ebankv1App.recentTransaction.status">Status</Translate>
                </Label>
                <AvInput
                  id="recent-transaction-status"
                  data-cy="status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && recentTransactionEntity.status) || 'PENDING'}
                >
                  <option value="PENDING">{translate('ebankv1App.TransactionStatus.PENDING')}</option>
                  <option value="COMPLETED">{translate('ebankv1App.TransactionStatus.COMPLETED')}</option>
                  <option value="CANCELED">{translate('ebankv1App.TransactionStatus.CANCELED')}</option>
                  <option value="DISPUTED">{translate('ebankv1App.TransactionStatus.DISPUTED')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="tCreatedLabel" for="recent-transaction-tCreated">
                  <Translate contentKey="ebankv1App.recentTransaction.tCreated">T Created</Translate>
                </Label>
                <AvInput
                  id="recent-transaction-tCreated"
                  data-cy="tCreated"
                  type="datetime-local"
                  className="form-control"
                  name="tCreated"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.recentTransactionEntity.tCreated)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/recent-transaction" replace color="info">
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
  recentTransactionEntity: storeState.recentTransaction.entity,
  loading: storeState.recentTransaction.loading,
  updating: storeState.recentTransaction.updating,
  updateSuccess: storeState.recentTransaction.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RecentTransactionUpdate);
