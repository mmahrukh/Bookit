package com.bookit.utilities;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BookitUtils {

    public static String getToken(String email, String password) {

        JsonPath jp = RestAssured.given().accept(ContentType.JSON)
                .queryParam("email", email)
                .queryParam("password", password)
                .when().get("/sign")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().jsonPath();

        String accessToken = jp.getString("accessToken");

        return "Bearer " + accessToken;

    }


    public static String generateTokenByRole(String role) {

        Map<String, String> roleCredentials = returnCredentials(role);
        String email = roleCredentials.get("email");
        String password = roleCredentials.get("password");

        return getToken(email, password);

    }

    public static Map<String, String> returnCredentials(String role) {
        String email = "";
        String password = "";

        switch (role) {
            case "teacher":
                // email = ConfigurationReader.getProperty("teacher_email") ;
                // password = ConfigurationReader.getProperty("teacher_password") ;
                email = System.getenv("TEACHER_EMAIL");
                password = System.getenv("TEACHER_PASSWORD");
                break;

            case "team_member":
                //email = ConfigurationReader.getProperty("team_member_email") ;
                //password = ConfigurationReader.getProperty("team_member_password");
                email = System.getenv("TEAM_MEMBER_EMAIL");
                password = System.getenv("TEAM_MEMBER_PASSWORD");
                break;
            case "team_leader":
                //email = ConfigurationReader.getProperty("team_leader_email") ;
                //password = ConfigurationReader.getProperty("team_leader_password") ;
                email = System.getenv("TEAM_LEADER_EMAIL");
                password = System.getenv("TEAM_LEADER_PASSWORD");
                break;

            default:

                throw new RuntimeException("Invalid Role Entry :\n>> " + role + " <<");
        }
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", email);
        credentials.put("password", password);

        return credentials;

    }

    public static Map<String, Object> createRandomStudent() {

        Faker faker = new Faker();
        Map<String, Object> userMap = new LinkedHashMap<>();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = firstName + "." + lastName + faker.number().numberBetween(1, 100) + "@cydeo.com";
        System.out.println(email);
        userMap.put("first-name", firstName);
        userMap.put("last-name", lastName);
        userMap.put("email", email);
        userMap.put("password", "Cydeo123");
        userMap.put("role", "student-team-member");
        userMap.put("campus-location", "VA");
        userMap.put("batch-number", "36");
        userMap.put("team-name", "Best Team Ever");

        return userMap;
    }


}