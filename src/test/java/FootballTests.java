import config.FootballConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static config.URLConstants.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

public class FootballTests extends FootballConfig {
    String areaParam = "areas";

    @Test
    public void getDetailsOfOneArea() {
        given()
                .queryParam(areaParam, 2076)
                .when()
                .get(AREA_ENDPOINT)
                .then()
                .body("areas[0].id", equalTo(2076));
    }

    @Test
    public void getDetailsOfMultipleAreas() {
        String areaId = "2076,2877,2080";
        given()
                .queryParam(areaParam, areaId)
                .when()
                .get(AREA_ENDPOINT)
                .then();
    }

    @Test
    public void getDateFounded() {
        given()
                .pathParam("team", "57")
                .when()
                .get(TEAMS_ENDPOINT + "/{team}")
                .then()
                .body("founded", equalTo(1886));
    }

    @Test
    public void getFirstTeamNameWithParam() {
        RestAssured.given()
                .pathParam("competition", "2021")
                .when()
                .get(COMPETITIONS_ENDPOINT + "/{competition}" + TEAMS_ENDPOINT)
                .then()
                .body("teams.name[0]", equalTo("Arsenal FC"));
    }

    @Test
    public void getFirstTeamName() {
        assertEquals(get("competitions/2021/teams")
                .jsonPath().getString("teams.name[0]"), "Arsenal FC");
    }

    @Test
    public void getTeamData2() {
        Response response =
                get("teams/57")
                        .then()
                        .contentType(ContentType.JSON)
                        .extract().response();

        String jsonResponseAsString = response.asString();
    }

    @Test
    public void extractHeaders() {
        Response response = get(TEAMS_ENDPOINT + "/57")
                .then()
                .extract().response();

        String contentTypeHeader = response.getContentType();
        String apiVersionHeader = response.getHeader("X-API-Version");
    }

    @Test
    public void extractTeamNames() {
        Response response = get("competitions/2021/teams")
                .then().extract().response();

        List<String> teamNames = response.path("teams.name");
        teamNames.forEach(System.out::println);
    }

    @Test
    public void assertStatusCode(){
        RestAssured.responseSpecification = null;
        Response response = RestAssured.get(FOOTBALL_URL + FOOTBALL_BASE_ENDPOINT + AREA_ENDPOINT);
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        assertEquals("application/json;charset=UTF-8", response.getContentType());
    }
}