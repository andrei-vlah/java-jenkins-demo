package api.testdata;

import api.models.Pet;
import com.github.javafaker.Faker;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.qameta.allure.Allure.step;

public class PetTestDataBuilder {

    private static final Faker faker = new Faker();

    public static Pet createDefaultPet() {
        return step("Generate default pet data", () -> {
            String petName = faker.dog().name();
            String categoryName = faker.animal().name();

            return Pet.builder()
                    .id(generateRandomId())
                    .category(Pet.Category.builder()
                            .id((long) faker.number().numberBetween(1, 100))
                            .name(categoryName)
                            .build())
                    .name(petName)
                    .photoUrls(generateRandomPhotoUrls())
                    .tags(generateRandomTags())
                    .status("available")
                    .build();
        });
    }

    public static Pet createPetWithName(String name) {
        return step("Set pet name: " + name, () -> {
            Pet pet = createDefaultPet();
            pet.setName(name);
            return pet;
        });
    }

    public static Pet createPetWithStatus(String status) {
        return step("Set pet status: " + status, () -> {
            Pet pet = createDefaultPet();
            pet.setStatus(status);
            return pet;
        });
    }

    public static Pet createMinimalPet() {
        return step("Generate minimal pet data", () -> Pet.builder()
                .name(faker.dog().name())
                .photoUrls(List.of(faker.internet().image()))
                .build());
    }

    private static Long generateRandomId() {
        return (long) faker.number().numberBetween(10000, 99999);
    }

    private static List<String> generateRandomPhotoUrls() {
        int photoCount = faker.number().numberBetween(1, 4);
        return IntStream.range(0, photoCount)
                .mapToObj(i -> faker.internet().image())
                .collect(Collectors.toList());
    }

    private static List<Pet.Tag> generateRandomTags() {
        int tagCount = faker.number().numberBetween(1, 5);
        return IntStream.range(0, tagCount)
                .mapToObj(i -> Pet.Tag.builder()
                        .id((long) faker.number().numberBetween(1, 1000))
                        .name(faker.dog().memePhrase().toLowerCase().replace(" ", "_"))
                        .build())
                .collect(Collectors.toList());
    }

    public static Pet createCustomPet(String name, String status, String categoryName) {
        return step(
                String.format("Create custom pet: name='%s', status='%s', category='%s'", name, status, categoryName),
                () -> Pet.builder()
                        .id(generateRandomId())
                        .category(Pet.Category.builder()
                                .id((long) faker.number().numberBetween(1, 100))
                                .name(categoryName)
                                .build())
                        .name(name)
                        .photoUrls(generateRandomPhotoUrls())
                        .tags(generateRandomTags())
                        .status(status)
                        .build());
    }
}