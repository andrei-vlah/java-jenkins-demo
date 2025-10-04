package api.testdata;

import api.models.Pet;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PetTestDataBuilder {

    private static final Faker faker = new Faker();
    private static final Logger logger = LoggerFactory.getLogger(PetTestDataBuilder.class);

    public static Pet createDefaultPet() {
        String petName = faker.dog().name();
        String categoryName = faker.animal().name();

        logger.debug("Creating default pet with name: {}", petName);

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
    }

    public static Pet createPetWithName(String name) {
        logger.debug("Creating pet with custom name: {}", name);

        return Pet.builder()
                .id(generateRandomId())
                .category(Pet.Category.builder()
                        .id((long) faker.number().numberBetween(1, 100))
                        .name(faker.animal().name())
                        .build())
                .name(name)
                .photoUrls(generateRandomPhotoUrls())
                .tags(generateRandomTags())
                .status("available")
                .build();
    }

    public static Pet createPetWithStatus(String status) {
        logger.debug("Creating pet with status: {}", status);

        return Pet.builder()
                .id(generateRandomId())
                .category(Pet.Category.builder()
                        .id((long) faker.number().numberBetween(1, 100))
                        .name(faker.animal().name())
                        .build())
                .name(faker.dog().name())
                .photoUrls(generateRandomPhotoUrls())
                .tags(generateRandomTags())
                .status(status)
                .build();
    }

    public static Pet createMinimalPet() {
        logger.debug("Creating minimal pet");

        return Pet.builder()
                .name(faker.dog().name())
                .photoUrls(List.of(faker.internet().image()))
                .build();
    }

    public static Pet createCustomPet(String name, String status, String categoryName) {
        logger.debug("Creating custom pet: name='{}', status='{}', category='{}'", name, status, categoryName);

        return Pet.builder()
                .id(generateRandomId())
                .category(Pet.Category.builder()
                        .id((long) faker.number().numberBetween(1, 100))
                        .name(categoryName)
                        .build())
                .name(name)
                .photoUrls(generateRandomPhotoUrls())
                .tags(generateRandomTags())
                .status(status)
                .build();
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
}