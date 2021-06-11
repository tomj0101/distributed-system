import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IIssueSystem } from 'app/shared/model/issue-system.model';
import { getEntities as getIssueSystems } from 'app/entities/issue-system/issue-system.reducer';
import { getEntity, updateEntity, createEntity, reset } from './customer-support.reducer';
import { ICustomerSupport } from 'app/shared/model/customer-support.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICustomerSupportUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CustomerSupportUpdate = (props: ICustomerSupportUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { customerSupportEntity, issueSystems, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/customer-support' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getIssueSystems();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.cCreated = convertDateTimeToServer(values.cCreated);

    if (errors.length === 0) {
      const entity = {
        ...customerSupportEntity,
        ...values,
        issueSystem: issueSystems.find(it => it.id.toString() === values.issueSystemId.toString()),
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
          <h2 id="ebankv1App.customerSupport.home.createOrEditLabel" data-cy="CustomerSupportCreateUpdateHeading">
            <Translate contentKey="ebankv1App.customerSupport.home.createOrEditLabel">Create or edit a CustomerSupport</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : customerSupportEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="customer-support-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="customer-support-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descriptionLabel" for="customer-support-description">
                  <Translate contentKey="ebankv1App.customerSupport.description">Description</Translate>
                </Label>
                <AvField
                  id="customer-support-description"
                  data-cy="description"
                  type="text"
                  name="description"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="cCreatedLabel" for="customer-support-cCreated">
                  <Translate contentKey="ebankv1App.customerSupport.cCreated">C Created</Translate>
                </Label>
                <AvInput
                  id="customer-support-cCreated"
                  data-cy="cCreated"
                  type="datetime-local"
                  className="form-control"
                  name="cCreated"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.customerSupportEntity.cCreated)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="severityLabel" for="customer-support-severity">
                  <Translate contentKey="ebankv1App.customerSupport.severity">Severity</Translate>
                </Label>
                <AvInput
                  id="customer-support-severity"
                  data-cy="severity"
                  type="select"
                  className="form-control"
                  name="severity"
                  value={(!isNew && customerSupportEntity.severity) || 'LOW'}
                >
                  <option value="LOW">{translate('ebankv1App.SupportSeverity.LOW')}</option>
                  <option value="MEDIUM">{translate('ebankv1App.SupportSeverity.MEDIUM')}</option>
                  <option value="HIGH">{translate('ebankv1App.SupportSeverity.HIGH')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="customer-support-status">
                  <Translate contentKey="ebankv1App.customerSupport.status">Status</Translate>
                </Label>
                <AvInput
                  id="customer-support-status"
                  data-cy="status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && customerSupportEntity.status) || 'OPEN'}
                >
                  <option value="OPEN">{translate('ebankv1App.SupportStatus.OPEN')}</option>
                  <option value="INPROGRESS">{translate('ebankv1App.SupportStatus.INPROGRESS')}</option>
                  <option value="CLOSE">{translate('ebankv1App.SupportStatus.CLOSE')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="customer-support-issueSystem">
                  <Translate contentKey="ebankv1App.customerSupport.issueSystem">Issue System</Translate>
                </Label>
                <AvInput
                  id="customer-support-issueSystem"
                  data-cy="issueSystem"
                  type="select"
                  className="form-control"
                  name="issueSystemId"
                >
                  <option value="" key="0" />
                  {issueSystems
                    ? issueSystems.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/customer-support" replace color="info">
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
  issueSystems: storeState.issueSystem.entities,
  customerSupportEntity: storeState.customerSupport.entity,
  loading: storeState.customerSupport.loading,
  updating: storeState.customerSupport.updating,
  updateSuccess: storeState.customerSupport.updateSuccess,
});

const mapDispatchToProps = {
  getIssueSystems,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CustomerSupportUpdate);
