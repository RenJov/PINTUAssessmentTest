package helper;

import io.restassured.response.Response;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;

import java.io.FileReader;

import static io.restassured.RestAssured.given;

public class ApiHelper {

    public Response getApiTest(String endpoint, String requestBody, Integer expectedHttpStatusCode) {
        Response responseGetApi = given()
                .when()
                .body(requestBody)
                .get(endpoint);

        Assert.assertEquals(responseGetApi.statusCode(),expectedHttpStatusCode);
        return responseGetApi;
    }

    public Response postApiTest(String endpoint, String requestBody, Integer expectedHttpStatusCode) {
        Response responsePostApi = given()
                .when()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(endpoint);

        Assert.assertEquals(responsePostApi.statusCode(),expectedHttpStatusCode);
        return responsePostApi;
    }

    public String getJsonStringFromFile (String filePath) throws Exception {
        String jsonString = null;
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            Object obj = jsonParser.parse(reader);
            jsonString = obj.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonString;
    }
}