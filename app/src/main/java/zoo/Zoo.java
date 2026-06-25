package zoo;

import zoo.animal.Animal;
import zoo.animal.Mammal;
import zoo.enclosure.Enclosure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        LOGGER.info("Adding enclosure: " + enclosure.getName());

        enclosures.add(enclosure);

        LOGGER.fine("Zoo now contains " + enclosures.size() + " enclosures.");
    }

    public List<Enclosure<? extends Animal>> getEnclosures() {
        return List.copyOf(enclosures);
    }

    public Enclosure<? extends Animal> findEnclosureByName(String name) {
        return enclosures.stream()
                .filter(enclosure -> enclosure.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<Animal> getAllAnimals() {
        return enclosures.stream()
                .flatMap(enclosure -> enclosure.getInhabitants().stream())
                .map(animal -> (Animal) animal)
                .toList();
    }

    public List<Mammal> getAllMammals() {
        return getAllAnimals().stream()
                .filter(Mammal.class::isInstance)
                .map(Mammal.class::cast)
                .toList();
    }

    public List<Animal> getAnimalsByPredicate(Predicate<Animal> predicate) {
        return getAllAnimals().stream()
                .filter(predicate)
                .toList();
    }

    public Map<Class<? extends Animal>, Long> countAnimalsByType() {
        return getAllAnimals().stream()
                .collect(Collectors.groupingBy(Animal::getClass, Collectors.counting()));
    }

    public List<Enclosure<? extends Animal>> getOvercrowdedEnclosures(int maxAnimals) {
        return enclosures.stream()
                .filter(enclosure -> enclosure.getInhabitants().size() > maxAnimals)
                .toList();
    }

    public String summary() {
        return "Zoo mit " + enclosures.size() + " Gehegen und "
                + getAllAnimals().size() + " Tieren";
    }
}