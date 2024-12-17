Feature: As a team_leader,
  I should be able to see campuses information

  Scenario: verify information about campuses
    Given I logged in Bookit api as a "team_leader"
    And request accept type is "application/json"
    When I sent GET request to "/api/campuses" endpoint
    Then status code should be 200
    And response content type is "application/json"
    And Each "id" field should not be null

  @wip
  Scenario Outline: verify information about related campuses
    Given I logged in Bookit api as a "team_leader"
    And request accept type is "application/json"
    And Path Param "campus_location" is "<campus>"
    When I sent GET request to "/api/campuses/{campus_location}" endpoint
    Then status code should be 200
    And response content type is "application/json"
    And "id" field should not be null
    And the field value for "location" path should be equal "<campus>"

    Examples:
      | campus |
      | VA     |
      | IL     |