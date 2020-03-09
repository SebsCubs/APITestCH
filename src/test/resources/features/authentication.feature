Feature: Authentication

  Scenario: Create a session ID
    Given a request token is generated
    When the request token is validated
    And a request to create the session ID is sent
    Then a session is opened with a new ID

  Scenario: Delete a session ID
    Given a session ID exists
    When a request to delete the ID is made
    Then the session ID os not usable anymore