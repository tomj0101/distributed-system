import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('OrderMaster e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/order-masters*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('order-master');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load OrderMasters', () => {
    cy.intercept('GET', '/api/order-masters*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('order-master');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('OrderMaster').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details OrderMaster page', () => {
    cy.intercept('GET', '/api/order-masters*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('order-master');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('orderMaster');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create OrderMaster page', () => {
    cy.intercept('GET', '/api/order-masters*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('order-master');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('OrderMaster');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit OrderMaster page', () => {
    cy.intercept('GET', '/api/order-masters*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('order-master');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('OrderMaster');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of OrderMaster', () => {
    cy.intercept('GET', '/api/order-masters*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('order-master');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('OrderMaster');

    cy.get(`[data-cy="paymentMethod"]`)
      .type('embrace protocol', { force: true })
      .invoke('val')
      .should('match', new RegExp('embrace protocol'));

    cy.get(`[data-cy="total"]`).type('31490').should('have.value', '31490');

    cy.get(`[data-cy="registerDate"]`).type('2021-06-19T19:34').invoke('val').should('equal', '2021-06-19T19:34');

    cy.setFieldSelectToLastOfEntity('address');

    cy.setFieldSelectToLastOfEntity('status');

    cy.setFieldSelectToLastOfEntity('user');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.intercept('GET', '/api/order-masters*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('order-master');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of OrderMaster', () => {
    cy.intercept('GET', '/api/order-masters*').as('entitiesRequest');
    cy.intercept('GET', '/api/order-masters/*').as('dialogDeleteRequest');
    cy.intercept('DELETE', '/api/order-masters/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('order-master');
    cy.wait('@entitiesRequest').then(({ request, response }) => {
      startingEntitiesCount = response.body.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('orderMaster').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest');
        cy.intercept('GET', '/api/order-masters*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('order-master');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
});
