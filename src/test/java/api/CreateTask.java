package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import utils.BaseAPITest;

public class CreateTask extends BaseAPITest {

    private String createdTaskId;

    static {
        RestAssured.baseURI = baseUrl;
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
    public void testCreateTask() {
        Response response = given()
            .header("Authorization", "Bearer " + api_token)
            .header("Content-Type", "application/json")
            .body("{\"content\": \"Newly created Task\", \"due_string\": \"tomorrow\"}")
        .when()
            .post("/tasks")
        .then()
            .statusCode(200)
            .body("content", equalTo("Newly created Task"))
            .body("is_completed", equalTo(false))
            .body("priority", equalTo(1))
            .body("creator_id", notNullValue())
            .body("id", notNullValue())
            .body("due.date", notNullValue())
            .extract().response();

        // Extract task ID for cleanup
        createdTaskId = response.path("id").toString();
    }

}
