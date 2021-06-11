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
    <MenuItem icon="asterisk" to="/recent-transaction">
      <Translate contentKey="global.menu.entities.recentTransaction" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/customer-support">
      <Translate contentKey="global.menu.entities.customerSupport" />
    </MenuItem>
     {/*
    <MenuItem icon="asterisk" to="/issue-system">
      <Translate contentKey="global.menu.entities.issueSystem" />
    </MenuItem> */}
    {/* needle-add-entity-to-menu - will add entities to the menu here */}
  </NavDropdown>
);
