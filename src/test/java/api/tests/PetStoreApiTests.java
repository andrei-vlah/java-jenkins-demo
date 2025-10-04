package api.tests;


import api.client.PetApiClient;
import api.models.Pet;
import api.testdata.PetTestDataBuilder;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class PetStoreApiTests {
    
    private PetApiClient petApiClient;
    
    @BeforeEach
    void setUp() {
        petApiClient = new PetApiClient();
    }
    
    @Test
    @DisplayName("Test 1: verify successful creation of a pet")
    void testCreateNewPet_Success() {
        Pet newPet = PetTestDataBuilder.createDefaultPet();
        
        Response response = petApiClient.createPet(newPet);

        response.then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", equalTo(newPet.getName()))
                .body("status", equalTo(newPet.getStatus()))
                .body("category.name", equalTo("Dogs"));
        
        Pet createdPet = response.as(Pet.class);
        assertNotNull(createdPet.getId(), "ID should be set up");
    }
    
    @Test
    @DisplayName("Test 2: verify get pet by id")
    void testGetPetById_Success() {
        Pet newPet = PetTestDataBuilder.createPetWithName("Buddy");
        Response createResponse = petApiClient.createPet(newPet);
        long petId = createResponse.jsonPath().getLong("id");

        Response response = petApiClient.getPetById(petId);

        response.then()
                .statusCode(200)
                .body("id", equalTo((int) petId))
                .body("name", equalTo("Buddy"));
    }
    
    @Test
    @DisplayName("Test 3: verify update of the existing pet")
    void testUpdateExistingPet_Success() {

        Pet originalPet = PetTestDataBuilder.createPetWithStatus("available");
        Response createResponse = petApiClient.createPet(originalPet);
        Pet createdPet = createResponse.as(Pet.class);

        createdPet.setName("Updated Name");
        createdPet.setStatus("sold");

        Response updateResponse = petApiClient.updatePet(createdPet);

        updateResponse.then()
                .statusCode(200)
                .body("name", equalTo("Updated Name"))
                .body("status", equalTo("sold"));
    }
    
    @Test
    @DisplayName("Test 4: verify pet search by status")
    void testFindPetsByStatus_ReturnsMultiplePets() {
        String status = "available";
        petApiClient.createPet(PetTestDataBuilder.createPetWithStatus(status));
        petApiClient.createPet(PetTestDataBuilder.createPetWithStatus(status));
        
        Response response = petApiClient.getPetsByStatus(status);
        
        response.then()
                .statusCode(200)
                .body("$", not(empty()))
                .body("status", everyItem(equalTo(status)));

        assertFalse(response.jsonPath().getList("$").isEmpty(), "Should return at least one pet");
    }
    
    @Test
    @DisplayName("Test 5: verify pet delete")
    void testDeletePet_Success() {
        Pet newPet = PetTestDataBuilder.createDefaultPet();
        Response createResponse = petApiClient.createPet(newPet);
        long petId = createResponse.jsonPath().getLong("id");
        
        Response deleteResponse = petApiClient.deletePet(petId);
        
        deleteResponse.then()
                .statusCode(200);
        
        Response getResponse = petApiClient.getPetById(petId);
        getResponse.then()
                .statusCode(404);
    }
}