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

describe('CustomerSupport e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.clearCookie('SESSION');
    cy.clearCookies();
    cy.intercept('GET', '/api/customer-supports*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('customer-support');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load CustomerSupports', () => {
    cy.intercept('GET', '/api/customer-supports*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('customer-support');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('CustomerSupport').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details CustomerSupport page', () => {
    cy.intercept('GET', '/api/customer-supports*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('customer-support');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('customerSupport');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create CustomerSupport page', () => {
    cy.intercept('GET', '/api/customer-supports*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('customer-support');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('CustomerSupport');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit CustomerSupport page', () => {
    cy.intercept('GET', '/api/customer-supports*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('customer-support');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('CustomerSupport');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of CustomerSupport', () => {
    cy.intercept('GET', '/api/customer-supports*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('customer-support');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('CustomerSupport');

    cy.get(`[data-cy="description"]`).type('Concrete', { force: true }).invoke('val').should('match', new RegExp('Concrete'));

    cy.get(`[data-cy="cCreated"]`).type('2021-06-11T16:17').invoke('val').should('equal', '2021-06-11T16:17');

    cy.get(`[data-cy="severity"]`).select('LOW');

    cy.get(`[data-cy="status"]`).select('INPROGRESS');

    cy.setFieldSelectToLastOfEntity('issueSystem');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.intercept('GET', '/api/customer-supports*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('customer-support');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of CustomerSupport', () => {
    cy.intercept('GET', '/api/customer-supports*').as('entitiesRequest');
    cy.intercept('GET', '/api/customer-supports/*').as('dialogDeleteRequest');
    cy.intercept('DELETE', '/api/customer-supports/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('customer-support');
    cy.wait('@entitiesRequest').then(({ request, response }) => {
      startingEntitiesCount = response.body.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('customerSupport').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest');
        cy.intercept('GET', '/api/customer-supports*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('customer-support');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
});
