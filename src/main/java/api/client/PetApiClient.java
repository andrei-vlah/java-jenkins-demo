package api.client;

import api.models.Pet;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.qameta.allure.Allure.step;

public class PetApiClient extends BaseApiClient {

    private static final String PET_ENDPOINT = "/pet";
    private static final String PET_BY_ID_ENDPOINT = "/pet/{petId}";
    private static final String PET_BY_STATUS_ENDPOINT = "/pet/findByStatus";

    public Response createPet(Pet pet) {
        return step("Create pet: " + pet, () -> post(PET_ENDPOINT, pet));
    }

    public Response updatePet(Pet pet) {
        return step("Update pet: " + pet, () -> put(PET_ENDPOINT, pet));
    }

    public Response getPetById(long petId) {
        return step("Get pet by id : " + petId, () -> get(PET_BY_ID_ENDPOINT.replace("{petId}", String.valueOf(petId))));
    }

    public Response deletePet(long petId) {
        return step("Delete pet by id: " + petId, () -> delete(PET_BY_ID_ENDPOINT.replace("{petId}", String.valueOf(petId)), "special-key"));
    }

    public Response getPetsByStatus(String status) {
        return step("get pet by status: " + status, () -> RestAssured.given()
                .spec(requestSpec)
                .queryParam("status", status)
                .when()
                .get(PET_BY_STATUS_ENDPOINT));
    }
}

