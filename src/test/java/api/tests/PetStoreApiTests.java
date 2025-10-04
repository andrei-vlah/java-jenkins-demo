package api.tests;

import api.models.Pet;
import api.testdata.PetTestDataBuilder;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.qameta.allure.Allure.step;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class PetStoreApiTests extends BaseApiTest {

    @Test
    @DisplayName("Test 1: Verify successful creation of a pet")
    void testCreateNewPet_Success() {
        Pet newPet = step("GIVEN: Prepare test pet data", PetTestDataBuilder::createDefaultPet);

        Pet createdPet = step("WHEN: Pet is created via API", () -> createTestPet(newPet));

        step("THEN: Verify created pet has correct data", () -> {
            assertAll("Pet Creation Validations",
                    () -> assertNotNull(createdPet.getId(), "ID should not be null"),
                    () -> assertEquals(newPet.getName(), createdPet.getName(), "Name should match"),
                    () -> assertEquals(newPet.getStatus(), createdPet.getStatus(), "Status should match"),
                    () -> assertNotNull(createdPet.getCategory(), "Category should be present")
            );
        });
    }

    @Test
    @DisplayName("Test 2: Verify get pet by id")
    void testGetPetById_Success() {
        Pet createdPet = step("GIVEN: Pet exists in the system", () ->
                createTestPet(PetTestDataBuilder.createPetWithName("Buddy"))
        );

        Response response = step("WHEN: Get pet by ID", () -> petApiClient.getPetById(createdPet.getId()));

        step("THEN: Verify response contains correct pet data", () -> {
            response.then()
                    .statusCode(HTTP_OK)
                    .body("id", equalTo(createdPet.getId().intValue()))
                    .body("name", equalTo(createdPet.getName()));
        });
    }

    @Test
    @DisplayName("Test 3: Verify update of the existing pet")
    void testUpdateExistingPet_Success() {
        Pet createdPet = step("GIVEN: Pet with 'available' status exists", () ->
                createTestPet(PetTestDataBuilder.createPetWithStatus("available"))
        );

        Response updateResponse = step("WHEN: Pet name and status are updated", () -> {
            createdPet.setName("Updated Name");
            createdPet.setStatus("sold");
            return petApiClient.updatePet(createdPet);
        });

        step("THEN: Verify pet was updated successfully", () -> {
            updateResponse.then()
                    .statusCode(HTTP_OK)
                    .body("name", equalTo("Updated Name"))
                    .body("status", equalTo("sold"));
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"available", "pending", "sold"})
    @DisplayName("Test 4: Verify pet search by different statuses")
    void testFindPetsByStatus_DifferentStatuses(String status) {
        step("GIVEN: Pet with status '" + status + "' exists", () ->
                createTestPet(PetTestDataBuilder.createPetWithStatus(status))
        );

        Response response = step("WHEN: Search pets by status '" + status + "'", () ->
                petApiClient.getPetsByStatus(status)
        );

        step("THEN: Verify all returned pets have correct status", () -> {
            response.then()
                    .statusCode(HTTP_OK)
                    .body("$", not(empty()))
                    .body("status", everyItem(equalTo(status)));
        });
    }

    @Test
    @DisplayName("Test 5: Verify pet delete")
    void testDeletePet_Success() {
        Pet createdPet = step("GIVEN: Pet exists in the system", () ->
                createTestPet(PetTestDataBuilder.createDefaultPet())
        );

        long petId = createdPet.getId();

        Response deleteResponse = step("WHEN: Pet is deleted", () -> petApiClient.deletePet(petId));

        step("THEN: Verify pet was deleted successfully", () -> verifyStatusCode(deleteResponse, HTTP_OK));

        step("AND: Pet cannot be found anymore", () -> {
            Response getResponse = petApiClient.getPetById(petId);
            verifyStatusCode(getResponse, HTTP_NOT_FOUND);
        });
        createdPetIds.remove(petId);
    }

    @Test
    @DisplayName("Test 6: Verify error when getting non-existent pet")
    void testGetPetById_NotFound() {
        long nonExistentId = 999999999L;

        Response response = step("WHEN: Try to get pet with non-existent ID", () ->
                petApiClient.getPetById(nonExistentId)
        );

        step("THEN: Verify 404 error is returned with appropriate message", () -> {
            response.then()
                    .statusCode(HTTP_NOT_FOUND)
                    .body("message", containsString("Pet not found"));
        });
    }

    @Test
    @DisplayName("Test 7: Verify validation error for invalid pet data")
    void testCreatePet_InvalidData() {
        Pet invalidPet = step("GIVEN: Prepare pet with invalid data (empty name)", () ->
                PetTestDataBuilder.createPetWithName("")
        );

        Response response = step("WHEN: Try to create pet with invalid data", () ->
                petApiClient.createPet(invalidPet)
        );

        step("THEN: Verify validation error is returned", () -> {
            response.then()
                    .statusCode(HTTP_BAD_REQUEST)
                    .body("message", notNullValue());
        });
    }
}