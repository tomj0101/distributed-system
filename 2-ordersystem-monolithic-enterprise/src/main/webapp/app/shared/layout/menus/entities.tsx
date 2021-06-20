import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/status">
      <Translate contentKey="global.menu.entities.status" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/address">
      <Translate contentKey="global.menu.entities.address" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/product">
      <Translate contentKey="global.menu.entities.product" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/order-master">
      <Translate contentKey="global.menu.entities.orderMaster" />
    </MenuItem>
    {/* needle-add-entity-to-menu - will add entities to the menu here */}
  </NavDropdown>
);
