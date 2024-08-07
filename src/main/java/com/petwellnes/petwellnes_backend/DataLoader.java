package com.petwellnes.petwellnes_backend;

import com.petwellnes.petwellnes_backend.repository.PetBreedRepository;
import com.petwellnes.petwellnes_backend.repository.PetTypeRepository;
import com.petwellnes.petwellnes_backend.repository.TopicRepository;
import com.petwellnes.petwellnes_backend.model.entity.PetBreed;
import com.petwellnes.petwellnes_backend.model.entity.PetType;
import com.petwellnes.petwellnes_backend.model.entity.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final PetTypeRepository petTypeRepository;
    private final PetBreedRepository petBreedRepository;
    private final TopicRepository topicRepository;

    @Override
    public void run(String... args) throws Exception {
        loadPetTypesAndBreeds();
        loadTopics();
    }

    private void loadPetTypesAndBreeds() {
        addPetTypeWithBreeds("Perro", new String[]{
                "Labrador Retriever", "Bulldog", "Beagle", "Poodle", "Rottweiler", "Yorkshire Terrier", "Boxer",
                "Dachshund", "Pastor Alemán", "Golden Retriever", "Shih Tzu", "Doberman Pinscher", "Chihuahua",
                "Gran Danés", "Husky Siberiano", "Callejero", "No Identificado"
        });

        addPetTypeWithBreeds("Gato", new String[]{
                "Siamés", "Persa", "Maine Coon", "Ragdoll", "British Shorthair", "Abisinio", "Bengala",
                "Sphynx", "Scottish Fold", "Azul Ruso", "Savannah", "Bosque de Noruega", "Oriental Shorthair",
                "Angora Turco", "Callejero", "No Identificado"
        });

        addPetTypeWithBreeds("Pez", new String[]{
                "Pez Payaso", "Pez Dorado", "Betta", "Guppy", "Tetra", "Pez Ángel", "Cíclido", "Bagre", "Molly",
                "Pez Espada", "Platy", "Óscar", "Koi", "Gurami", "Neón Tetra", "Disco", "No Identificado"
        });

        addPetTypeWithBreeds("Roedor", new String[]{
                "Cuy", "Conejo", "Hámster", "Ratón", "Rata", "Jerbo", "Chinchilla", "Ardilla", "Perro de la pradera",
                "Degu", "No Identificado"
        });

        addPetTypeWithBreeds("Ave", new String[]{
                "Periquito", "Canario", "Cacatúa", "Agapornis", "Pinzón", "Pájaro Budgie", "Loro", "Guacamayo",
                "Conuro", "Gris Africano", "Ecléctico", "Loriquito", "Paloma", "Tórtola", "No Identificado"
        });

        addPetTypeWithBreeds("Reptil", new String[]{
                "Tortuga", "Camaleón", "Iguana", "Geco", "Dragón Barbudo", "Serpiente", "Tortuga de Tierra",
                "Lagarto Monitor", "Anole", "Eslizón", "Boa", "Pitón", "Cobra", "Caimán", "Cocodrilo", "No Identificado"
        });

        addPetTypeWithBreeds("Otro", new String[]{
                "Caballo", "Oveja", "Cabra", "Cerdo", "Vaca", "Llama", "Alpaca", "Burro", "No Identificado"
        });
    }

    private void addPetTypeWithBreeds(String typeName, String[] breedNames) {
        Optional<PetType> optionalPetType = petTypeRepository.findByName(typeName);
        PetType petType = optionalPetType.orElseGet(() -> {
            PetType newType = new PetType();
            newType.setName(typeName);
            return petTypeRepository.save(newType);
        });

        for (String breedName : breedNames) {
            if (!petBreedRepository.existsByNameAndPetType(breedName, petType)) {
                PetBreed breed = new PetBreed();
                breed.setName(breedName);
                breed.setPetType(petType);
                petBreedRepository.save(breed);
            }
        }
    }

    private void loadTopics() {
        if (topicRepository.count() == 0) {
            addTopic("General");
            addTopic("Comportamiento");
            addTopic("Alimentación");
            addTopic("Salud");
            addTopic("Moda");
        }
    }

    private void addTopic(String topicName) {
        if (!topicRepository.existsByName(topicName)) {
            Topic topic = new Topic();
            topic.setName(topicName);
            topicRepository.save(topic);
        }
    }
}
