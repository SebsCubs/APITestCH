Feature: Retrieve collections info

  Scenario: Get collection details
    When a details request for John Wick's collection is made
    Then the collection's info is obtained

  Scenario: Get collection images
    When an images request for John Wick's collection is made
    Then the collection's images info is obtained

  Scenario: Get collection translations
    When a translations request for John Wick's collection is made
    Then the collection's translations are obtained