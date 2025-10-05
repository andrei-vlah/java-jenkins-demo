package api.tests;

import api.client.PetApiClient;
import api.models.Pet;
import config.ConfigProvider;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_OK;

abstract class BaseApiTest {
    protected PetApiClient petApiClient;
    protected List<Long> createdPetIds;
    private static final Logger logger = LoggerFactory.getLogger(BaseApiTest.class);

    @BeforeAll
    public static void setUpApi() {
        RestAssured.baseURI = ConfigProvider.getApiConfig().baseUrl();

        if (ConfigProvider.getApiConfig().logRequests()) {
            RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        }
    }

    @BeforeEach
    void baseSetUp() {
        petApiClient = new PetApiClient();
        createdPetIds = new ArrayList<>();
    }

    @AfterEach
    void baseTearDown() {
        cleanupTestData();
    }

    protected void cleanupTestData() {
        logger.info("Cleaning up {} test pets", createdPetIds.size());
        createdPetIds.forEach(id -> {
            try {
                petApiClient.deletePet(id);
                logger.debug("Deleted pet with ID: {}", id);
            } catch (Exception e) {
                logger.warn("Failed to delete pet with ID: {}", id, e);
            }
        });
        createdPetIds.clear();
    }

    @Step("Setup: Create test pet '{pet.name}'")
    protected Pet createTestPet(Pet pet) {
        logger.debug("Creating test pet: {}", pet.getName());
        Response response = petApiClient.createPet(pet);
        response.then().statusCode(HTTP_OK);
        Pet createdPet = response.as(Pet.class);
        createdPetIds.add(createdPet.getId());
        logger.info("Created pet with ID: {}", createdPet.getId());
        return createdPet;
    }

    @Step("Verify response status code: {expectedStatusCode}")
    protected void verifyStatusCode(Response response, int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }
}