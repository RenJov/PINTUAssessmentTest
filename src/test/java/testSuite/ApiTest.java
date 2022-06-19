package testSuite;

import helper.ApiHelper;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;


public class ApiTest {

    ApiHelper apiHelper = new ApiHelper();

    private final static String ENDPOINT = "https://jsonplaceholder.typicode.com/posts";
    private final static String JSON_POST_REQUEST = "src/main/resources/postRequest.json";

    @Test(description="GET, Check data-type of the response compared to the requirement")
    public void getTest() {

        Response response = apiHelper.getApiTest(ENDPOINT, "", 200);
        System.out.println("GET Response: "+response.getBody().asString());

        JSONArray jsonArray = new JSONArray(response.getBody().asString());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            assertDataType(null, jsonObject);
        }
    }

    @Test(description="POST, Check data-type of the response compared to the request")
    public void postTest() throws Exception {

        String requestJsonString = apiHelper.getJsonStringFromFile(JSON_POST_REQUEST);
        JSONObject requestJson = new JSONObject(requestJsonString);
        System.out.println("POST Request: "+requestJson.toString(2));

        Response response = apiHelper.postApiTest(ENDPOINT, requestJsonString, 201);
        JSONObject responseJson = new JSONObject(response.getBody().asString());
        System.out.println("POST Response: "+responseJson.toString(2));
        assertDataType(requestJson, responseJson);

    }

    public void assertDataType(JSONObject jsonRequest, JSONObject jsonResponse) {

        String expectedUserIdDataType = "Integer";
        String expectedIdDataType = "Integer";
        String expectedTitleDataType = "String";
        String expectedBodyDataType = "String";

        if (jsonRequest != null) {
            expectedUserIdDataType = jsonRequest.get("userId").getClass().getSimpleName();
            expectedTitleDataType = jsonRequest.get("title").getClass().getSimpleName();
            expectedBodyDataType = jsonRequest.get("body").getClass().getSimpleName();
        }

        //assert userId data type
        String userIdDataType = jsonResponse.get("userId").getClass().getSimpleName();
        Assert.assertEquals(expectedUserIdDataType, userIdDataType);

        //assert id data type
        String idDataType = jsonResponse.get("id").getClass().getSimpleName();
        Assert.assertEquals(expectedIdDataType, idDataType);

        //assert title data type
        String titleDataType = jsonResponse.get("title").getClass().getSimpleName();
        Assert.assertEquals(expectedTitleDataType, titleDataType);

        //assert body data type
        String bodyDataType = jsonResponse.get("body").getClass().getSimpleName();
        Assert.assertEquals(expectedBodyDataType, bodyDataType);
    }
}
