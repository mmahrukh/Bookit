package com.bookit.step_definitions;

import com.bookit.utilities.BookitUtils;
import com.github.javafaker.Team;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class ApiStepDefs {

    RequestSpecification givenPart = RestAssured.given().log().all();
    Response response;
    JsonPath jp;
    ValidatableResponse thenPart;

    @Given("I logged in Bookit api as a {string}")
    public void i_logged_in_bookit_api_as_a(String role) {
        givenPart.header("Authorization", BookitUtils.generateTokenByRole(role));
    }

    @Given("request accept type is {string}")
    public void request_accept_type_is(String acceptHeader) {
        givenPart.accept(acceptHeader);
    }

    @When("I sent GET request to {string} endpoint")
    public void i_sent_get_request_to_endpoint(String endpoint) {
        response = givenPart.when().get(endpoint);
        jp = response.jsonPath();
        thenPart = response.then();

        response.prettyPrint();
    }

    @Then("status code should be {int}")
    public void status_code_should_be(int expectedStatusCode) {
        // OPT1 - RESPONSE
        Assert.assertEquals(expectedStatusCode, response.statusCode());
        // OPT2 - THENPART
        thenPart.statusCode(expectedStatusCode);

    }

    @Then("response content type is {string}")
    public void response_content_type_is(String expectedContentType) {
        // OPT1 - RESPONSE
        Assert.assertEquals(expectedContentType, response.contentType());
        // OPT2 - THENPART
        thenPart.contentType(expectedContentType);
    }

    @Then("Each {string} field should not be null")
    public void each_field_should_not_be_null(String path) {
        // OPT1 - JSONPATH
        List<Object> allFields = jp.getList(path);
        for (Object eachField : allFields) {
            Assert.assertNotNull(eachField);
        }

        // OPT2 - THENPART
        thenPart.body(path, everyItem(notNullValue()));

    }


    /**
     * Scenario 2
     */

    @Given("Path Param {string} is {string}")
    public void path_param_is(String pathParamKey, String pathParamValue) {
        givenPart.pathParam(pathParamKey, pathParamValue);

    }

    @Then("{string} field should not be null")
    public void field_should_not_be_null(String path) {
        String value = jp.getString(path);
        Assert.assertNotNull(value);
    }

    @Then("the field value for {string} path should be equal {string}")
    public void the_field_value_for_path_should_be_equal(String path, String expectedValue) {
        String actualValue = jp.getString(path);
        Assert.assertEquals(expectedValue, actualValue);

    }

    /**
     * /students endpoint
     */
    @Then("following fields should not be null")
    public void following_fields_should_not_be_null(List<String> paths) {

        for (String path : paths) {

            // OPT 1
            thenPart.body(path, Matchers.notNullValue());

            // OPT 2
            Assert.assertNotNull(jp.getString(path));
        }

    }
    /**
     *  POST /student
     */
    Map<String, Object> randomDataMap;
    @Given("I create a random {string} as a query param")
    public void i_create_a_random_as_a_query_param(String dataType) {

        switch(dataType){

            case "student":
                randomDataMap = BookitUtils.createRandomStudent();
                break;
            case "team":
                // randomDataMap = BookitUtils.createRandomTeam();
                break;
            default:
                throw new RuntimeException("Wrong data type is provide");
        }

        givenPart.queryParams(randomDataMap);



    }
    @When("I sent POST request to {string} endpoint")
    public void i_sent_post_request_to_endpoint(String endpoint) {
        response = givenPart.when().post(endpoint);
        jp = response.jsonPath();
        thenPart = response.then();

        response.prettyPrint();
    }
    @Then("the field value for {string} should contains created user fullname")
    public void the_field_value_for_should_contains_created_user_fullname(String path) {

        // Get value from response
        String message = jp.getString(path);
        System.out.println("message = " + message);
        String fullname=randomDataMap.get("first-name")+" "+randomDataMap.get("last-name");
        System.out.println("fullname = " + fullname);

        Assert.assertTrue(message.contains(fullname));
    }

    @Then("created student should be persist in database")
    public void created_student_should_be_persist_in_database() {

    }


}


