import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, openFile, byteSize, Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './product.reducer';
import { IProduct } from 'app/shared/model/product.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProductUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProductUpdate = (props: IProductUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { productEntity, loading, updating } = props;

  const { description, productImages, productImagesContentType } = productEntity;

  const handleClose = () => {
    props.history.push('/product' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.registerDate = convertDateTimeToServer(values.registerDate);

    if (errors.length === 0) {
      const entity = {
        ...productEntity,
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
          <h2 id="ordersystemApp.product.home.createOrEditLabel" data-cy="ProductCreateUpdateHeading">
            <Translate contentKey="ordersystemApp.product.home.createOrEditLabel">Create or edit a Product</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : productEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="product-id">
                    <Translate contentKey="ordersystemApp.product.id">Id</Translate>
                  </Label>
                  <AvInput id="product-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="product-name">
                  <Translate contentKey="ordersystemApp.product.name">Name</Translate>
                </Label>
                <AvField
                  id="product-name"
                  data-cy="name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    minLength: { value: 1, errorMessage: translate('entity.validation.minlength', { min: 1 }) },
                    maxLength: { value: 250, errorMessage: translate('entity.validation.maxlength', { max: 250 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="product-description">
                  <Translate contentKey="ordersystemApp.product.description">Description</Translate>
                </Label>
                <AvInput id="product-description" data-cy="description" type="textarea" name="description" />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="productImagesLabel" for="productImages">
                    <Translate contentKey="ordersystemApp.product.productImages">Product Images</Translate>
                  </Label>
                  <br />
                  {productImages ? (
                    <div>
                      {productImagesContentType ? (
                        <a onClick={openFile(productImagesContentType, productImages)}>
                          <img src={`data:${productImagesContentType};base64,${productImages}`} style={{ maxHeight: '100px' }} />
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {productImagesContentType}, {byteSize(productImages)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('productImages')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input
                    id="file_productImages"
                    data-cy="productImages"
                    type="file"
                    onChange={onBlobChange(true, 'productImages')}
                    accept="image/*"
                  />
                  <AvInput type="hidden" name="productImages" value={productImages} />
                </AvGroup>
              </AvGroup>
              <AvGroup>
                <Label id="priceLabel" for="product-price">
                  <Translate contentKey="ordersystemApp.product.price">Price</Translate>
                </Label>
                <AvField id="product-price" data-cy="price" type="string" className="form-control" name="price" />
              </AvGroup>
              <AvGroup>
                <Label id="conditionLabel" for="product-condition">
                  <Translate contentKey="ordersystemApp.product.condition">Condition</Translate>
                </Label>
                <AvInput
                  id="product-condition"
                  data-cy="condition"
                  type="select"
                  className="form-control"
                  name="condition"
                  value={(!isNew && productEntity.condition) || 'NEW'}
                >
                  <option value="NEW">{translate('ordersystemApp.Condition.NEW')}</option>
                  <option value="USED">{translate('ordersystemApp.Condition.USED')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup check>
                <Label id="activeLabel">
                  <AvInput id="product-active" data-cy="active" type="checkbox" className="form-check-input" name="active" />
                  <Translate contentKey="ordersystemApp.product.active">Active</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="registerDateLabel" for="product-registerDate">
                  <Translate contentKey="ordersystemApp.product.registerDate">Register Date</Translate>
                </Label>
                <AvInput
                  id="product-registerDate"
                  data-cy="registerDate"
                  type="datetime-local"
                  className="form-control"
                  name="registerDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.productEntity.registerDate)}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/product" replace color="info">
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
  productEntity: storeState.product.entity,
  loading: storeState.product.loading,
  updating: storeState.product.updating,
  updateSuccess: storeState.product.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductUpdate);
