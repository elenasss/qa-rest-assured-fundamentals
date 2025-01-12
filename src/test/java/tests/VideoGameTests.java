package tests;

import config.VideoGameConfig;
import config.URLConstants;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pojo.VideoGame;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.lessThan;

public class VideoGameTests extends VideoGameConfig {

    String gameBodyJson = "{\n" +
            "  \"category\": \"Platform\",\n" +
            "  \"name\": \"Mario\",\n" +
            "  \"rating\": \"Mature\",\n" +
            "  \"releaseDate\": \"2012-05-04\",\n" +
            "  \"reviewScore\": 85\n" +
            "}";

    @Test
    public void getAllGames(){
        given()
        .when()
                .get(URLConstants.ALL_VIDEO_GAMES_ENDPOINT)
        .then();
    }

    @Test
    public void createNewGameByJSON(){
        given()
                .body(gameBodyJson)
        .when()
                .post(URLConstants.ALL_VIDEO_GAMES_ENDPOINT)
        .then();
    }

    @Test
    public void updateGame(){
        given()
                .pathParam("videoGameId", 3)
                .body(gameBodyJson)
        .when()
                .put(URLConstants.SINGLE_VIDEO_GAME_ENDPOINT)
        .then();
    }

    @Test
    public void deleteGame(){
        given()
                .accept("text/plain")
                .pathParam("videoGameId", 5)
        .when()
                .delete(URLConstants.SINGLE_VIDEO_GAME_ENDPOINT)
        .then();
    }

    @Test
    public void testVideoGameSerialization(){
        VideoGame videoGame = new VideoGame("Shooter", "MyNewGame", "Mature", "2018-04-20", 8);

        given()
                .body(videoGame)
        .when()
                .post(URLConstants.ALL_VIDEO_GAMES_ENDPOINT)
        .then();
    }

    @Test
    public void testVideoGameSchemaJson(){
        given()
                .pathParam("videoGameId", 5)
        .when()
                .get(URLConstants.SINGLE_VIDEO_GAME_ENDPOINT)
        .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("VideoGameJsonSchema.json"));
    }

    @Test
    public void convertJsonToPojo(){
        Response response =
                given()
                        .pathParam("videoGameId", 5)
                .when()
                        .get(URLConstants.SINGLE_VIDEO_GAME_ENDPOINT);

        VideoGame videoGame = response.getBody().as(VideoGame.class);
    }

    @Test
    public void captureResponseTime(){
      long responseTime = get(URLConstants.ALL_VIDEO_GAMES_ENDPOINT).time();

      get(URLConstants.ALL_VIDEO_GAMES_ENDPOINT)
              .then().time(lessThan(1000L));
    }
}