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

describe('IssueSystem e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.clearCookie('SESSION');
    cy.clearCookies();
    cy.intercept('GET', '/api/issue-systems*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('issue-system');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load IssueSystems', () => {
    cy.intercept('GET', '/api/issue-systems*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('issue-system');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('IssueSystem').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details IssueSystem page', () => {
    cy.intercept('GET', '/api/issue-systems*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('issue-system');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('issueSystem');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create IssueSystem page', () => {
    cy.intercept('GET', '/api/issue-systems*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('issue-system');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('IssueSystem');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit IssueSystem page', () => {
    cy.intercept('GET', '/api/issue-systems*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('issue-system');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('IssueSystem');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of IssueSystem', () => {
    cy.intercept('GET', '/api/issue-systems*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('issue-system');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('IssueSystem');

    cy.get(`[data-cy="title"]`).type('Cloned Lanka', { force: true }).invoke('val').should('match', new RegExp('Cloned Lanka'));

    cy.get(`[data-cy="description"]`).type('up', { force: true }).invoke('val').should('match', new RegExp('up'));

    cy.get(`[data-cy="iCreated"]`).type('2021-06-10T19:13').invoke('val').should('equal', '2021-06-10T19:13');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.intercept('GET', '/api/issue-systems*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('issue-system');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of IssueSystem', () => {
    cy.intercept('GET', '/api/issue-systems*').as('entitiesRequest');
    cy.intercept('GET', '/api/issue-systems/*').as('dialogDeleteRequest');
    cy.intercept('DELETE', '/api/issue-systems/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('issue-system');
    cy.wait('@entitiesRequest').then(({ request, response }) => {
      startingEntitiesCount = response.body.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('issueSystem').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest');
        cy.intercept('GET', '/api/issue-systems*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('issue-system');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
});
