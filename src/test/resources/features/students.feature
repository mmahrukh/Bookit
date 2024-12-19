Feature: As a teacher
  I should able to manipulate students


  Scenario: verify information about students
    Given I logged in Bookit api as a "teacher"
    And request accept type is "application/json"
    When I sent GET request to "/api/students" endpoint
    Then status code should be 200
    And response content type is "application/json"
    And Each "id" field should not be null
    And Each "role" field should not be null
  @wip
  Scenario: verify information about single student
    Given I logged in Bookit api as a "teacher"
    And request accept type is "application/json"
    And Path Param "id" is "16985"
    When I sent GET request to "/api/students" endpoint
    Then status code should be 200
    And response content type is "application/json"
    And following fields should not be null
      | id        |
      | firstName |
      | lastName  |
      | role      |