import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './order-details.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrderDetailsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OrderDetailsDetail = (props: IOrderDetailsDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { orderDetailsEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="orderDetailsDetailsHeading">
          <Translate contentKey="ordersystemApp.orderDetails.detail.title">OrderDetails</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="ordersystemApp.orderDetails.id">Id</Translate>
            </span>
          </dt>
          <dd>{orderDetailsEntity.id}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="ordersystemApp.orderDetails.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{orderDetailsEntity.userId}</dd>
          <dt>
            <span id="registerDate">
              <Translate contentKey="ordersystemApp.orderDetails.registerDate">Register Date</Translate>
            </span>
          </dt>
          <dd>
            {orderDetailsEntity.registerDate ? (
              <TextFormat value={orderDetailsEntity.registerDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="ordersystemApp.orderDetails.orderMaster">Order Master</Translate>
          </dt>
          <dd>{orderDetailsEntity.orderMaster ? orderDetailsEntity.orderMaster.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/order-details" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/order-details/${orderDetailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ orderDetails }: IRootState) => ({
  orderDetailsEntity: orderDetails.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OrderDetailsDetail);
