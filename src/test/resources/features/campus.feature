Feature: As a team_leader,
  I should be able to see campuses information

  @wip
  Scenario: verify information about logged user
    Given I logged in Bookit api as a "team_leader"
    And request accept type is "application/json"
    When I sent GET request to "/api/campuses" endpoint
    Then status code should be 200
    And response content type is "application/json"
    And Each "id" field should not be null
