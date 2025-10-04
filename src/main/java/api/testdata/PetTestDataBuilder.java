package api.testdata;

import api.models.Pet;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PetTestDataBuilder {
    
    private static final Random random = new Random();

    public static Pet createDefaultPet() {
        return Pet.builder()
                .id(generateRandomId())
                .category(Pet.Category.builder()
                        .id(1L)
                        .name("Dogs")
                        .build())
                .name("Rex")
                .photoUrls(List.of("http://example.com/photo1.jpg"))
                .tags(Arrays.asList(
                        Pet.Tag.builder().id(1L).name("friendly").build(),
                        Pet.Tag.builder().id(2L).name("vaccinated").build()
                ))
                .status("available")
                .build();
    }

    public static Pet createPetWithName(String name) {
        Pet pet = createDefaultPet();
        pet.setName(name);
        return pet;
    }

    public static Pet createPetWithStatus(String status) {
        Pet pet = createDefaultPet();
        pet.setStatus(status);
        return pet;
    }

    public static Pet createMinimalPet() {
        return Pet.builder()
                .name("Minimal Pet")
                .photoUrls(List.of("http://example.com/photo.jpg"))
                .build();
    }

    private static Long generateRandomId() {
        return 10000L + random.nextInt(90000);
    }
}