import config.FootballConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.get;


public class GpathJSONTests extends FootballConfig {

    @Test
    public void extractMapOfElementsWithFind(){
        Response response = get("competitions/2021/teams");
        Map<String, ?> allTeamDataForSingleTeam = response.path("teams.find {it.name == 'Manchester United FC' }");
        System.out.println("Map of team data: " + allTeamDataForSingleTeam);
    }

    @Test
    public void extractSingleValueWIthFind(){
        Response response = get("teams/57");
        String certainPlayer = response.path("squad.find {it.id == 4832 }.name");
        System.out.println("Player: " + certainPlayer);
    }

    @Test
    public void extractListOfValuesWIthFindAll(){
        Response response = get("teams/57");
        List<String> playerNames = response.path("squad.findAll {it.id >= 7784}.name");
        System.out.println("List of players: " + playerNames);
    }

    @Test
    public void extractValueWIthHighestNumber(){
        Response response = get("teams/57");
        String playerName = response.path("squad.max {it.id}.name");
        System.out.println("Player with highest id: " + playerName);
    }

    @Test
    public void extractValueWIthLowestNumber(){
        Response response = get("teams/57");
        String playerName = response.path("squad.min {it.id}.name");
        System.out.println("Player with highest id: " + playerName);
    }

    @Test
    public void extractMultipleValuesAndSumThem(){
        Response response = get("teams/57");
        int sumOfIds = response.path("squad.collect {it.id}.sum()");
        System.out.println("The sum of all ids: " + sumOfIds);
    }

    @Test
    public void extractMapWithFindWithParameters(){
        String position = "Goalkeeper";
        String nationality = "Spain";

        Response response = get("teams/57");
        Map<String, ?> playerOfCertainPosition = response.path
                ("squad.findAll{it.position == '%s'}.find{it.nationality == '%s'}", position, nationality);
        System.out.println("The player is: " + playerOfCertainPosition);
    }

    @Test
    public void extractMultiplePlayersWithFindWithParameters(){
        String position = "Goalkeeper";
        String nationality = "England";

        Response response = get("teams/57");
        List<Map<String, ?>> playersOfCertainPosition = response.path
                ("squad.findAll{it.position == '%s'}.findAll{it.nationality == '%s'}", position, nationality);
        System.out.println("Details of players: " + playersOfCertainPosition);
    }
}