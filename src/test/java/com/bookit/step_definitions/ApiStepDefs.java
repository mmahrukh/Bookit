package com.bookit.step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ApiStepDefs {

    @Given("I logged in Bookit api as a {string}")
    public void i_logged_in_bookit_api_as_a(String role) {

    }
    @Given("request accept type is {string}")
    public void request_accept_type_is(String acceptHeader) {

    }
    @When("I sent GET request to {string} endpoint")
    public void i_sent_get_request_to_endpoint(String endpoint) {

    }
    @Then("status code should be {int}")
    public void status_code_should_be(int expectedStatusCode) {

    }
    @Then("response content type is {string}")
    public void response_content_type_is(String expectedContentType) {

    }
    @Then("Each {string} field should not be null")
    public void each_field_should_not_be_null(String path) {


    }


}
