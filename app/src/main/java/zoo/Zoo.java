package zoo;

import zoo.animal.Animal;
import zoo.animal.Bird;
import zoo.animal.Fish;
import zoo.animal.Mammal;
import zoo.animal.Reptile;
import zoo.enclosure.Enclosure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Zoo {

    private static final Logger LOGGER = Logger.getLogger(Zoo.class.getName());

    private final List<Enclosure<? extends Animal>> enclosures;

    public Zoo() {
        this.enclosures = new ArrayList<>();
    }

    public void addEnclosure(Enclosure<? extends Animal> enclosure) {
        LOGGER.info("addEnclosure(" + enclosure + ")");

        if (enclosure == null) {
            LOGGER.severe("Cannot add null enclosure.");
            return;
        }

        enclosures.add(enclosure);

        LOGGER.fine("Zoo contains " + enclosures.size() + " enclosures.");
    }

    public List<Enclosure<? extends Animal>> getEnclosures() {
        LOGGER.info("getEnclosures()");
        LOGGER.fine("Returning " + enclosures.size() + " enclosures.");
        return List.copyOf(enclosures);
    }

    public Enclosure<? extends Animal> findEnclosureByName(String name) {
        LOGGER.info("findEnclosureByName(" + name + ")");

        Enclosure<? extends Animal> enclosure = enclosures.stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(null);

        if (enclosure == null) {
            LOGGER.warning("No enclosure found with name: " + name);
        } else {
            LOGGER.fine("Enclosure found.");
        }

        return enclosure;
    }

    public Optional<Animal> findAnimalByName(String animalName) {
        LOGGER.info("findAnimalByName(" + animalName + ")");

        Optional<Animal> animal = enclosures.stream()
                .flatMap(enclosure -> enclosure.findAnimalByName(animalName).stream())
                .map(a -> (Animal) a)
                .findFirst();

        if (animal.isEmpty()) {
            LOGGER.warning("No animal found with name: " + animalName);
        } else {
            LOGGER.fine("Animal found: " + animal.get().name());
        }

        return animal;
    }

    public List<Animal> getAllAnimals() {
        LOGGER.info("getAllAnimals()");

        List<Animal> animals = enclosures.stream()
                .flatMap(e -> e.getInhabitants().stream())
                .map(a -> (Animal) a)
                .toList();

        LOGGER.fine("Found " + animals.size() + " animals.");

        return animals;
    }

    public List<Mammal> getAllMammals() {
        LOGGER.info("getAllMammals()");

        List<Mammal> mammals = getAllAnimals().stream()
                .filter(Mammal.class::isInstance)
                .map(Mammal.class::cast)
                .toList();

        LOGGER.fine("Found " + mammals.size() + " mammals.");

        return mammals;
    }

    public List<Animal> getAnimalsByPredicate(Predicate<Animal> predicate) {
        LOGGER.info("getAnimalsByPredicate()");

        List<Animal> animals = getAllAnimals().stream()
                .filter(predicate)
                .toList();

        LOGGER.fine("Predicate returned " + animals.size() + " animals.");

        return animals;
    }

    public Map<Class<? extends Animal>, Long> countAnimalsByType() {
        LOGGER.info("countAnimalsByType()");

        Map<Class<? extends Animal>, Long> result = getAllAnimals().stream()
                .collect(Collectors.groupingBy(Animal::getClass, Collectors.counting()));

        LOGGER.fine("Counted " + result.size() + " animal types.");

        return result;
    }

    public List<Enclosure<? extends Animal>> getOvercrowdedEnclosures(int maxAnimals) {
        LOGGER.info("getOvercrowdedEnclosures(" + maxAnimals + ")");

        List<Enclosure<? extends Animal>> result = enclosures.stream()
                .filter(e -> e.getInhabitants().size() > maxAnimals)
                .toList();

        LOGGER.fine("Found " + result.size() + " overcrowded enclosures.");

        return result;
    }

    public String summary() {
        LOGGER.info("summary()");

        long mammals = getAllAnimals().stream().filter(Mammal.class::isInstance).count();
        long birds = getAllAnimals().stream().filter(Bird.class::isInstance).count();
        long fish = getAllAnimals().stream().filter(Fish.class::isInstance).count();
        long reptiles = getAllAnimals().stream().filter(Reptile.class::isInstance).count();

        String summary = "Zoo mit " + enclosures.size() + " Gehegen und "
                + getAllAnimals().size() + " Tieren: "
                + mammals + " Mammals, "
                + birds + " Birds, "
                + fish + " Fish, "
                + reptiles + " Reptiles";

        LOGGER.fine(summary);

        return summary;
    }
}