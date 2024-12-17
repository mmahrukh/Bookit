package com.bookit.step_definitions;

import com.bookit.utilities.BookitUtils;
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
        thenPart.statusCode(200);

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
        thenPart.body(path, Matchers.everyItem(Matchers.notNullValue()));

    }


}
