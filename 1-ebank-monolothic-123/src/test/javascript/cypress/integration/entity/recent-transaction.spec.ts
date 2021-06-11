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

describe('RecentTransaction e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.clearCookie('SESSION');
    cy.clearCookies();
    cy.intercept('GET', '/api/recent-transactions*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('recent-transaction');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load RecentTransactions', () => {
    cy.intercept('GET', '/api/recent-transactions*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('recent-transaction');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('RecentTransaction').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details RecentTransaction page', () => {
    cy.intercept('GET', '/api/recent-transactions*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('recent-transaction');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('recentTransaction');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create RecentTransaction page', () => {
    cy.intercept('GET', '/api/recent-transactions*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('recent-transaction');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('RecentTransaction');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit RecentTransaction page', () => {
    cy.intercept('GET', '/api/recent-transactions*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('recent-transaction');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('RecentTransaction');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of RecentTransaction', () => {
    cy.intercept('GET', '/api/recent-transactions*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('recent-transaction');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('RecentTransaction');

    cy.get(`[data-cy="description"]`).type('Pizza', { force: true }).invoke('val').should('match', new RegExp('Pizza'));

    cy.get(`[data-cy="amount"]`).type('16415').should('have.value', '16415');

    cy.get(`[data-cy="status"]`).select('CANCELED');

    cy.get(`[data-cy="tCreated"]`).type('2021-06-11T02:39').invoke('val').should('equal', '2021-06-11T02:39');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.intercept('GET', '/api/recent-transactions*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('recent-transaction');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of RecentTransaction', () => {
    cy.intercept('GET', '/api/recent-transactions*').as('entitiesRequest');
    cy.intercept('GET', '/api/recent-transactions/*').as('dialogDeleteRequest');
    cy.intercept('DELETE', '/api/recent-transactions/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('recent-transaction');
    cy.wait('@entitiesRequest').then(({ request, response }) => {
      startingEntitiesCount = response.body.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('recentTransaction').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest');
        cy.intercept('GET', '/api/recent-transactions*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('recent-transaction');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
});
