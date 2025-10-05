package api.client;

import config.ConfigProvider;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public abstract class BaseApiClient {

    protected static final String BASE_URL = ConfigProvider.getApiConfig().baseUrl();
    protected RequestSpecification requestSpec;

    public BaseApiClient() {
        this.requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    @Step("GET {endpoint}")
    protected Response get(String endpoint) {
        return RestAssured.given()
                .spec(requestSpec)
                .when()
                .get(endpoint);
    }

    @Step("POST {endpoint}")
    protected Response post(String endpoint, Object body) {
        return RestAssured.given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post(endpoint);
    }

    @Step("PUT {endpoint}")
    protected Response put(String endpoint, Object body) {
        return RestAssured.given()
                .spec(requestSpec)
                .body(body)
                .when()
                .put(endpoint);
    }

    @Step("DELETE {endpoint}")
    protected Response delete(String endpoint) {
        return RestAssured.given()
                .spec(requestSpec)
                .when()
                .delete(endpoint);
    }

    @Step("DELETE {endpoint} with API key")
    protected Response delete(String endpoint, String apiKey) {
        return RestAssured.given()
                .spec(requestSpec)
                .header("api_key", apiKey)
                .when()
                .delete(endpoint);
    }
}