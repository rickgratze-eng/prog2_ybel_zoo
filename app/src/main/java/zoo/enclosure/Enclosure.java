package zoo.enclosure;

import zoo.animal.Animal;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Enclosure<T extends Animal> {

    private final String name;
    private final Set<T> inhabitants;

    public Enclosure(String name) {
        this.name = name;
        this.inhabitants = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public boolean add(T animal) {
        return inhabitants.add(animal);
    }

    public boolean remove(T animal) {
        return inhabitants.remove(animal);
    }

    public List<T> getInhabitants() {
        return List.copyOf(inhabitants);
    }
}