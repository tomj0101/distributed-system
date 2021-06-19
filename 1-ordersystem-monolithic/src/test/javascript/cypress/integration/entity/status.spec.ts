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

describe('Status e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.clearCookie('SESSION');
    cy.clearCookies();
    cy.intercept('GET', '/api/statuses*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('status');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load Statuses', () => {
    cy.intercept('GET', '/api/statuses*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('status');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('Status').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details Status page', () => {
    cy.intercept('GET', '/api/statuses*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('status');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('status');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create Status page', () => {
    cy.intercept('GET', '/api/statuses*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('status');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Status');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit Status page', () => {
    cy.intercept('GET', '/api/statuses*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('status');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('Status');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of Status', () => {
    cy.intercept('GET', '/api/statuses*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('status');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Status');

    cy.get(`[data-cy="name"]`).type('Rubber', { force: true }).invoke('val').should('match', new RegExp('Rubber'));

    cy.get(`[data-cy="description"]`).type('well-modulated', { force: true }).invoke('val').should('match', new RegExp('well-modulated'));

    cy.get(`[data-cy="registerDate"]`).type('2021-06-19T05:15').invoke('val').should('equal', '2021-06-19T05:15');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.intercept('GET', '/api/statuses*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('status');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of Status', () => {
    cy.intercept('GET', '/api/statuses*').as('entitiesRequest');
    cy.intercept('GET', '/api/statuses/*').as('dialogDeleteRequest');
    cy.intercept('DELETE', '/api/statuses/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('status');
    cy.wait('@entitiesRequest').then(({ request, response }) => {
      startingEntitiesCount = response.body.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('status').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest');
        cy.intercept('GET', '/api/statuses*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('status');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
});
