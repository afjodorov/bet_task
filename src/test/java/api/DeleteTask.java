package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import utils.BaseAPITest;

public class DeleteTask extends BaseAPITest {

    private String createdTaskId;

    static {
        RestAssured.baseURI = baseUrl;
    }

    @BeforeEach
    public void createTask() {
        Response response = given()
            .header("Authorization", "Bearer " + api_token)
            .header("Content-Type", "application/json")
            .body("{\"content\": \"Task to be deleted\", \"due_string\": \"tomorrow\"}")
        .when()
            .post("/tasks")
        .then()
            .statusCode(200)
            .extract().response();
        
        createdTaskId = response.path("id").toString();
    }    

    @Test
    public void testDeleteTask() {
        given()
            .header("Authorization", "Bearer " + api_token)
        .when()
            .delete("/tasks/" + createdTaskId)
        .then()
            .statusCode(204);
    }
    
}
