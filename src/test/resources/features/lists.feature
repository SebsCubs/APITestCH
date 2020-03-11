Feature: Interact with account's lists

  Background:
    Given user authenticates and generates a session

  Scenario: Create a new list
    When a new list is created with the data
      |name      |description           |language|
      |myListEndv|tha besto listo       |en      |
    Then a list is created and a list_id is generated
    And the session closes

  Scenario: Delete a list
    Given a new list is created with the data
      |name      |description           |language|
      |myListEndv|tha besto listo       |en      |

    When the list is deleted
    Then the list and list_id are not available anymore
    And the session closes

  Scenario: Delete a non existing list
    Given the list with id "12242456" doesn't exist
    When an attempt to delete the list is made
    Then an answer with a resource not found is received

  Scenario: Add a movie to a list
    Given a new list is created with the data
      |name      |description           |language|
      |myListEndv|tha besto listo       |en      |
    When the movie with id "299534" is added
    Then the list has the movie added
    And the session closes

  Scenario: Remove a movie from a list
    Given a new list is created with the data
      |name      |description           |language|
      |myListEndv|tha besto listo       |en      |
    And the list contains at least one movie
    When the first movie is removed from the list
    Then the list doesn't have the movie in details
    And the session closes

  Scenario: Adding a movie already existing in a list
    Given a new list is created with the data
      |name      |description           |language|
      |myListEndv|tha besto listo       |en      |
    And the list contains at least one movie
    When an attempt to add a list already present in the list is made
    Then an invalid movie message is received

  Scenario: Clear a list
    Given a new list is created with the data
      |name      |description           |language|
      |myListEndv|tha besto listo       |en      |
    And the movie with id "448119" is added
    When the list is cleared
    Then the list has no items

