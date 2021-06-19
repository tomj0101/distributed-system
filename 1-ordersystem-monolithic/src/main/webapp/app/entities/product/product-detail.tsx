import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './product.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProductDetail = (props: IProductDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { productEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productDetailsHeading">
          <Translate contentKey="ordersystemApp.product.detail.title">Product</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="ordersystemApp.product.id">Id</Translate>
            </span>
          </dt>
          <dd>{productEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="ordersystemApp.product.name">Name</Translate>
            </span>
          </dt>
          <dd>{productEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="ordersystemApp.product.description">Description</Translate>
            </span>
          </dt>
          <dd>{productEntity.description}</dd>
          <dt>
            <span id="productImages">
              <Translate contentKey="ordersystemApp.product.productImages">Product Images</Translate>
            </span>
          </dt>
          <dd>
            {productEntity.productImages ? (
              <div>
                {productEntity.productImagesContentType ? (
                  <a onClick={openFile(productEntity.productImagesContentType, productEntity.productImages)}>
                    <img
                      src={`data:${productEntity.productImagesContentType};base64,${productEntity.productImages}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {productEntity.productImagesContentType}, {byteSize(productEntity.productImages)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="price">
              <Translate contentKey="ordersystemApp.product.price">Price</Translate>
            </span>
          </dt>
          <dd>{productEntity.price}</dd>
          <dt>
            <span id="condition">
              <Translate contentKey="ordersystemApp.product.condition">Condition</Translate>
            </span>
          </dt>
          <dd>{productEntity.condition}</dd>
          <dt>
            <span id="active">
              <Translate contentKey="ordersystemApp.product.active">Active</Translate>
            </span>
          </dt>
          <dd>{productEntity.active ? 'true' : 'false'}</dd>
          <dt>
            <span id="registerDate">
              <Translate contentKey="ordersystemApp.product.registerDate">Register Date</Translate>
            </span>
          </dt>
          <dd>
            {productEntity.registerDate ? <TextFormat value={productEntity.registerDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/product" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/product/${productEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ product }: IRootState) => ({
  productEntity: product.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductDetail);
