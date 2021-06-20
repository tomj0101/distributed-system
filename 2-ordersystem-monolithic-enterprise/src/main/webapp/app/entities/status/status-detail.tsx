import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './status.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStatusDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const StatusDetail = (props: IStatusDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { statusEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="statusDetailsHeading">
          <Translate contentKey="ordersystemv2App.status.detail.title">Status</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="ordersystemv2App.status.id">Id</Translate>
            </span>
          </dt>
          <dd>{statusEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="ordersystemv2App.status.name">Name</Translate>
            </span>
          </dt>
          <dd>{statusEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="ordersystemv2App.status.description">Description</Translate>
            </span>
          </dt>
          <dd>{statusEntity.description}</dd>
          <dt>
            <span id="registerDate">
              <Translate contentKey="ordersystemv2App.status.registerDate">Register Date</Translate>
            </span>
          </dt>
          <dd>
            {statusEntity.registerDate ? <TextFormat value={statusEntity.registerDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/status" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/status/${statusEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ status }: IRootState) => ({
  statusEntity: status.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(StatusDetail);
