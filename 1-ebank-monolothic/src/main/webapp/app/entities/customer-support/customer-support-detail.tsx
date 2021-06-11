import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './customer-support.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICustomerSupportDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CustomerSupportDetail = (props: ICustomerSupportDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { customerSupportEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="customerSupportDetailsHeading">
          <Translate contentKey="ebankv1App.customerSupport.detail.title">CustomerSupport</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{customerSupportEntity.id}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="ebankv1App.customerSupport.description">Description</Translate>
            </span>
          </dt>
          <dd>{customerSupportEntity.description}</dd>
          <dt>
            <span id="cCreated">
              <Translate contentKey="ebankv1App.customerSupport.cCreated">C Created</Translate>
            </span>
          </dt>
          <dd>
            {customerSupportEntity.cCreated ? (
              <TextFormat value={customerSupportEntity.cCreated} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="severity">
              <Translate contentKey="ebankv1App.customerSupport.severity">Severity</Translate>
            </span>
          </dt>
          <dd>{customerSupportEntity.severity}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="ebankv1App.customerSupport.status">Status</Translate>
            </span>
          </dt>
          <dd>{customerSupportEntity.status}</dd>
          <dt>
            <Translate contentKey="ebankv1App.customerSupport.issueSystem">Issue System</Translate>
          </dt>
          <dd>{customerSupportEntity.issueSystem ? customerSupportEntity.issueSystem.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/customer-support" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/customer-support/${customerSupportEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ customerSupport }: IRootState) => ({
  customerSupportEntity: customerSupport.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CustomerSupportDetail);
