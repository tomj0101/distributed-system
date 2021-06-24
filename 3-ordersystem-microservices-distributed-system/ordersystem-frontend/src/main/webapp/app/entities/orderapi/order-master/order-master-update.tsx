import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './order-master.reducer';
import { IOrderMaster } from 'app/shared/model/orderapi/order-master.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOrderMasterUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OrderMasterUpdate = (props: IOrderMasterUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { orderMasterEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/order-master' + props.location.search);
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
    values.registerDate = convertDateTimeToServer(values.registerDate);

    if (errors.length === 0) {
      const entity = {
        ...orderMasterEntity,
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
          <h2 id="ordersystemfrontendApp.orderapiOrderMaster.home.createOrEditLabel" data-cy="OrderMasterCreateUpdateHeading">
            <Translate contentKey="ordersystemfrontendApp.orderapiOrderMaster.home.createOrEditLabel">
              Create or edit a OrderMaster
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : orderMasterEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="order-master-id">
                    <Translate contentKey="ordersystemfrontendApp.orderapiOrderMaster.id">Id</Translate>
                  </Label>
                  <AvInput id="order-master-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="paymentMethodLabel" for="order-master-paymentMethod">
                  <Translate contentKey="ordersystemfrontendApp.orderapiOrderMaster.paymentMethod">Payment Method</Translate>
                </Label>
                <AvField id="order-master-paymentMethod" data-cy="paymentMethod" type="text" name="paymentMethod" />
              </AvGroup>
              <AvGroup>
                <Label id="totalLabel" for="order-master-total">
                  <Translate contentKey="ordersystemfrontendApp.orderapiOrderMaster.total">Total</Translate>
                </Label>
                <AvField id="order-master-total" data-cy="total" type="string" className="form-control" name="total" />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="order-master-email">
                  <Translate contentKey="ordersystemfrontendApp.orderapiOrderMaster.email">Email</Translate>
                </Label>
                <AvField
                  id="order-master-email"
                  data-cy="email"
                  type="text"
                  name="email"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    minLength: { value: 1, errorMessage: translate('entity.validation.minlength', { min: 1 }) },
                    maxLength: { value: 500, errorMessage: translate('entity.validation.maxlength', { max: 500 }) },
                    pattern: {
                      value: '^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$',
                      errorMessage: translate('entity.validation.pattern', { pattern: '^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$' }),
                    },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="registerDateLabel" for="order-master-registerDate">
                  <Translate contentKey="ordersystemfrontendApp.orderapiOrderMaster.registerDate">Register Date</Translate>
                </Label>
                <AvInput
                  id="order-master-registerDate"
                  data-cy="registerDate"
                  type="datetime-local"
                  className="form-control"
                  name="registerDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.orderMasterEntity.registerDate)}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/order-master" replace color="info">
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
  orderMasterEntity: storeState.orderMaster.entity,
  loading: storeState.orderMaster.loading,
  updating: storeState.orderMaster.updating,
  updateSuccess: storeState.orderMaster.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OrderMasterUpdate);
