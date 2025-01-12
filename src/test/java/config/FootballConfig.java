package config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeEach;

import static config.URLConstants.FOOTBALL_BASE_ENDPOINT;
import static config.URLConstants.FOOTBALL_URL;

public class FootballConfig {

    private final String TOKEN = "b38d7f7e3f7e47c2b88a829df9b9c8eb";

    @BeforeEach
    public void setup(){
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(FOOTBALL_URL)
                .setBasePath(FOOTBALL_BASE_ENDPOINT)
                .addHeader("X-Auth-Token", TOKEN)
                .addHeader("X-Response-Control", "minified")
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }
}