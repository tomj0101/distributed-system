import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IOrderMaster } from 'app/shared/model/order-master.model';
import { getEntities as getOrderMasters } from 'app/entities/order-master/order-master.reducer';
import { getEntity, updateEntity, createEntity, reset } from './order-details.reducer';
import { IOrderDetails } from 'app/shared/model/order-details.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOrderDetailsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OrderDetailsUpdate = (props: IOrderDetailsUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { orderDetailsEntity, orderMasters, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/order-details');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getOrderMasters();
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
        ...orderDetailsEntity,
        ...values,
        orderMaster: orderMasters.find(it => it.id.toString() === values.orderMasterId.toString()),
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
          <h2 id="ordersystemApp.orderDetails.home.createOrEditLabel" data-cy="OrderDetailsCreateUpdateHeading">
            <Translate contentKey="ordersystemApp.orderDetails.home.createOrEditLabel">Create or edit a OrderDetails</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : orderDetailsEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="order-details-id">
                    <Translate contentKey="ordersystemApp.orderDetails.id">Id</Translate>
                  </Label>
                  <AvInput id="order-details-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              {/**
              <AvGroup>
                <Label id="userIdLabel" for="order-details-userId">
                  <Translate contentKey="ordersystemApp.orderDetails.userId">User Id</Translate>
                </Label>
                <AvField id="order-details-userId" data-cy="userId" type="string" className="form-control" name="userId" />
              </AvGroup>
              */}
              {/**
              <AvGroup>
                <Label id="registerDateLabel" for="order-details-registerDate">
                  <Translate contentKey="ordersystemApp.orderDetails.registerDate">Register Date</Translate>
                </Label>
                <AvInput
                  id="order-details-registerDate"
                  data-cy="registerDate"
                  type="datetime-local"
                  className="form-control"
                  name="registerDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.orderDetailsEntity.registerDate)}
                />
              </AvGroup>
                */}
              <AvGroup>
                <Label for="order-details-orderMaster">
                  <Translate contentKey="ordersystemApp.orderDetails.orderMaster">Order Master</Translate>
                </Label>
                <AvInput id="order-details-orderMaster" data-cy="orderMaster" type="select" className="form-control" name="orderMasterId">
                  <option value="" key="0" />
                  {orderMasters
                    ? orderMasters.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/order-details" replace color="info">
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
  orderMasters: storeState.orderMaster.entities,
  orderDetailsEntity: storeState.orderDetails.entity,
  loading: storeState.orderDetails.loading,
  updating: storeState.orderDetails.updating,
  updateSuccess: storeState.orderDetails.updateSuccess,
});

const mapDispatchToProps = {
  getOrderMasters,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OrderDetailsUpdate);
