Feature: Rate movies

  Background:
    Given user authenticates and generates a session

  Scenario: Rate a movie
    Given the movie Jumanji: The Next Level doesn't have a rate from the test account
    When the movie Jumanji:The Next Level is rated with "5.0"
    Then the movie has now a new rate

  Scenario: Rate an already rated movie
    Given the movie Jumanji:The Next Level is rated with "5.0"
    When the movie Jumanji:The Next Level is rated with "5.0"
    Then the rate is updated

  Scenario: Delete the rate of a movie
    Given the movie Jumanji:The Next Level has a rate from the test account
    When the rate is eliminated
    Then the movie has a rate less

  Scenario: Delete a non-existing rate
    Given the movie Jumanji: The Next Level doesn't have a rate from the test account
    When the rate is eliminated
    Then an error of no permissions is prompted

