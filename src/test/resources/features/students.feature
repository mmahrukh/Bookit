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

  Scenario: verify information about single student
    Given I logged in Bookit api as a "teacher"
    And request accept type is "application/json"
    And Path Param "id" is "16985"
    When I sent GET request to "/api/students/{id}" endpoint
    Then status code should be 200
    And response content type is "application/json"
    And following fields should not be null
      | id        |
      | firstName |
      | lastName  |
      | role      |



  Scenario: create a student
    Given I logged in Bookit api as a "teacher"
    And request accept type is "application/json"
    And I create a random "student" as a query param
    When I sent POST request to "/api/students/student" endpoint
    Then status code should be 201
    And response content type is "application/json"
    And "entryiId" field should not be null
    And the field value for "entryType" path should be equal "Student"
    And the field value for "message" should contains created user fullname


  @wip
  Scenario: create a student and verify user exist in database
    Given I logged in Bookit api as a "teacher"
    And request accept type is "application/json"
    And I create a random "student" as a query param
    When I sent POST request to "/api/students/student" endpoint
    Then status code should be 201
    And response content type is "application/json"
    And "entryiId" field should not be null
    And the field value for "entryType" path should be equal "Student"
    And the field value for "message" should contains created user fullname
    And created student should be persist in database
