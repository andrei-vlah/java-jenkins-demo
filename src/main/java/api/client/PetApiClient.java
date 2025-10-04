package api.client;

import api.models.Pet;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class PetApiClient extends BaseApiClient {

    private static final String PET_ENDPOINT = "/pet";
    private static final String PET_BY_ID_ENDPOINT = "/pet/{petId}";
    private static final String PET_BY_STATUS_ENDPOINT = "/pet/findByStatus";

    public Response createPet(Pet pet) {
        return post(PET_ENDPOINT, pet);
    }

    public Response updatePet(Pet pet) {
        return put(PET_ENDPOINT, pet);
    }

    public Response getPetById(long petId) {
        return get(PET_BY_ID_ENDPOINT.replace("{petId}", String.valueOf(petId)));
    }

    public Response deletePet(long petId) {
        return delete(PET_BY_ID_ENDPOINT.replace("{petId}", String.valueOf(petId)), "special-key");
    }

    public Response getPetsByStatus(String status) {
        return RestAssured.given()
                .spec(requestSpec)
                .queryParam("status", status)
                .when()
                .get(PET_BY_STATUS_ENDPOINT);
    }
}

