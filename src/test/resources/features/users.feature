Feature: User feature

  Scenario: verify information about logged in user
    Given I logged in Bookit api as a "team_leader"
    And request accept type is "application/json"
    When I sent GET request to "api/users/me" endpoint
    Then status code should be 200
    And response content type is "application/json"
    And "id" field should not be null
    And the field value for "role" path should be equal "student-team-leader"


  Scenario Outline: verify information about logged in user "<role>"
    Given I logged in Bookit api as a "<role>"
    And request accept type is "application/json"
    When I sent GET request to "api/users/me" endpoint
    Then status code should be 200
    And response content type is "application/json"
    And "id" field should not be null
    And the field value for "role" path should be equal "<value>"
    Examples:
      | role        | value               |
      | teacher     | teacher             |
      | team_leader | student-team-leader |
      | team_member | student-team-member |