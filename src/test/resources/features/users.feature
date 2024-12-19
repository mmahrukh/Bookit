Feature: User feature
@wip
  Scenario: verify information about logged in user
    Given I logged in Bookit api as a "team_leader"
    And request accept type is "application/json"
    When I sent GET request to "api/users/me" endpoint
    Then status code should be 200
    And response content type is "application/json"
    And "id" field should not be null
    And the field value for "role" path should be equal "student-team-leader"
