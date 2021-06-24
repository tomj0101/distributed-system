import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './order-master.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrderMasterDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OrderMasterDetail = (props: IOrderMasterDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { orderMasterEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="orderMasterDetailsHeading">
          <Translate contentKey="ordersystemfrontendApp.orderapiOrderMaster.detail.title">OrderMaster</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="ordersystemfrontendApp.orderapiOrderMaster.id">Id</Translate>
            </span>
          </dt>
          <dd>{orderMasterEntity.id}</dd>
          <dt>
            <span id="paymentMethod">
              <Translate contentKey="ordersystemfrontendApp.orderapiOrderMaster.paymentMethod">Payment Method</Translate>
            </span>
          </dt>
          <dd>{orderMasterEntity.paymentMethod}</dd>
          <dt>
            <span id="total">
              <Translate contentKey="ordersystemfrontendApp.orderapiOrderMaster.total">Total</Translate>
            </span>
          </dt>
          <dd>{orderMasterEntity.total}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="ordersystemfrontendApp.orderapiOrderMaster.email">Email</Translate>
            </span>
          </dt>
          <dd>{orderMasterEntity.email}</dd>
          <dt>
            <span id="registerDate">
              <Translate contentKey="ordersystemfrontendApp.orderapiOrderMaster.registerDate">Register Date</Translate>
            </span>
          </dt>
          <dd>
            {orderMasterEntity.registerDate ? (
              <TextFormat value={orderMasterEntity.registerDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/order-master" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/order-master/${orderMasterEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ orderMaster }: IRootState) => ({
  orderMasterEntity: orderMaster.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OrderMasterDetail);
