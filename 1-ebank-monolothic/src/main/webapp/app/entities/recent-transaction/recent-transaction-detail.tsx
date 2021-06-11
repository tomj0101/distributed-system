import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './recent-transaction.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRecentTransactionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RecentTransactionDetail = (props: IRecentTransactionDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { recentTransactionEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="recentTransactionDetailsHeading">
          <Translate contentKey="ebankv1App.recentTransaction.detail.title">RecentTransaction</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{recentTransactionEntity.id}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="ebankv1App.recentTransaction.description">Description</Translate>
            </span>
          </dt>
          <dd>{recentTransactionEntity.description}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="ebankv1App.recentTransaction.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{recentTransactionEntity.amount}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="ebankv1App.recentTransaction.status">Status</Translate>
            </span>
          </dt>
          <dd>{recentTransactionEntity.status}</dd>
          <dt>
            <span id="tCreated">
              <Translate contentKey="ebankv1App.recentTransaction.tCreated">T Created</Translate>
            </span>
          </dt>
          <dd>
            {recentTransactionEntity.tCreated ? (
              <TextFormat value={recentTransactionEntity.tCreated} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/recent-transaction" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/recent-transaction/${recentTransactionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ recentTransaction }: IRootState) => ({
  recentTransactionEntity: recentTransaction.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RecentTransactionDetail);
