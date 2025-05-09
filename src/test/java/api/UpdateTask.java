package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import utils.BaseAPITest;


public class UpdateTask extends BaseAPITest {

    private String createdTaskId;

    static {
        RestAssured.baseURI = baseUrl;
    }

    @BeforeEach
    public void createTask() {
        Response response = given()
            .header("Authorization", "Bearer " + api_token)
            .header("Content-Type", "application/json")
            .body("{\"content\": \"Task to be updated\", \"due_string\": \"tomorrow\"}")
        .when()
            .post("/tasks")
        .then()
            .statusCode(200)
            .extract().response();
        
        createdTaskId = response.path("id").toString();
    }

    @AfterEach
    public void deleteTask() {
        given()
            .header("Authorization", "Bearer " + api_token)
        .when()
            .delete("/tasks/" + createdTaskId)
        .then()
            .statusCode(204);
    }

    @Test
    public void testUpdateTask() {
        given()
            .header("Authorization", "Bearer " + api_token)
            .header("Content-Type", "application/json")
            .body("{\"content\": \"Updated Task\", \"due_string\": \"next week\"}")
        .when()
            .post("/tasks/" + createdTaskId)
        .then()
            .statusCode(200)
            .body("content", equalTo("Updated Task"))
            .body("due.string", equalTo("next week"));
    }
    
}
