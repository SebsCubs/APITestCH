Feature: Interact with account's lists

  Background:
    Given User authenticates and generates a session

  Scenario: Create a new list
    When A new list is created with the data
    |name       |description |language|
    |myListaPrro|Bien perrona|en      |
    Then A list is created and a list_id is generated
    And The session closes


  Scenario: Delete a list
    Given A new list is created with the data
      |name       |description |language|
      |myListaPrro|Bien perrona|en      |
    When The list is deleted
    Then The list and list_id are not available anymore
    And The session closes

  Scenario: Add a movie to a list
    Given A list exists with id "133851"
    When The movie with id "448119" is added
    Then The list has the movie added and doesn't appear in details
    And The session closes

  Scenario: Remove a movie from a list
    Given A list exists with id "133851"
    And The list contains the movie with id "448119"
    When The movie is removed from the list
    Then The list doesn't have the movie in details
    And The session closes

  Scenario: Clear a list
    Given A list exists with id "133851"
    And The movie with id "448119" is added
    When The list is cleared
    Then The list has no items