package config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeEach;

import static config.URLConstants.VIDEO_GAME_ENDPOINT;
import static config.URLConstants.VIDEO_GAME_URL;
import static org.hamcrest.Matchers.lessThan;

public class VideoGameConfig {
    @BeforeEach
    public void setup(){
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(VIDEO_GAME_URL)
                .setBasePath(VIDEO_GAME_ENDPOINT)
                .setContentType("application/json")
                .addHeader("Accept", "application/json")
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectResponseTime(lessThan(3000L))
                .build();
    }
}