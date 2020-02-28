Feature: Rate movies

  Background:
    Given User authenticates and generates a session

  Scenario: Rate a movie
    When The movie with id "449924" is rated with "8.5"
    Then The response has a valid status code