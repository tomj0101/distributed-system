import {
  currentPasswordSelector,
  newPasswordSelector,
  confirmPasswordSelector,
  submitPasswordSelector,
  classInvalid,
  classValid,
} from '../../support/commands';

describe('/account/password', () => {
  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.clearCookie('SESSION');
    cy.clearCookies();
    cy.visit('');
    cy.login('user', 'user');
    cy.clickOnPasswordItem();
  });

  beforeEach(() => {
    cy.intercept('POST', '/api/account/change-password').as('passwordSave');
  });

  it('requires current password', () => {
    cy.get(currentPasswordSelector).should('have.class', classInvalid).type('wrong-current-password').should('have.class', classValid);
    cy.get(currentPasswordSelector).clear();
  });

  it('requires new password', () => {
    cy.get(newPasswordSelector).should('have.class', classInvalid).type('tom').should('have.class', classValid);
    cy.get(newPasswordSelector).clear();
  });

  it('requires confirm new password', () => {
    cy.get(newPasswordSelector).type('tom');
    cy.get(confirmPasswordSelector).should('have.class', classInvalid).type('tom').should('have.class', classValid);
    cy.get(newPasswordSelector).clear();
    cy.get(confirmPasswordSelector).clear();
  });

  it('should fail to update password when using incorrect current password', () => {
    cy.get(currentPasswordSelector).type('wrong-current-password');
    cy.get(newPasswordSelector).type('tom');
    cy.get(confirmPasswordSelector).type('tom');
    cy.get(submitPasswordSelector).click({ force: true });
    cy.wait('@passwordSave').then(({ request, response }) => expect(response.statusCode).to.equal(400));
    cy.get(currentPasswordSelector).clear();
    cy.get(newPasswordSelector).clear();
    cy.get(confirmPasswordSelector).clear();
  });

  it('should be able to update password', () => {
    cy.get(currentPasswordSelector).type('user');
    cy.get(newPasswordSelector).type('user');
    cy.get(confirmPasswordSelector).type('user');
    cy.get(submitPasswordSelector).click({ force: true });
    cy.wait('@passwordSave').then(({ request, response }) => expect(response.statusCode).to.equal(200));
  });
});
