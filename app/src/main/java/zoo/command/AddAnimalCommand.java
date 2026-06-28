package zoo.command;

import zoo.animal.Animal;
import zoo.enclosure.Enclosure;

import java.util.logging.Logger;

public class AddAnimalCommand<T extends Animal> implements Command<Enclosure<? super T>> {

    private static final Logger LOGGER = Logger.getLogger(AddAnimalCommand.class.getName());

    private final T animal;
    private boolean executed;

    public AddAnimalCommand(T animal) {
        this.animal = animal;
        this.executed = false;
    }

    @Override
    public boolean execute(Enclosure<? super T> target) {
        boolean added = target.add(animal);

        if (!added) {
            LOGGER.warning("Animal could not be added: " + animal.name());
        }

        executed = added;
        return added;
    }

    @Override
    public boolean undo(Enclosure<? super T> target) {
        if (!executed) {
            LOGGER.warning("Undo not possible before command was executed.");
            return false;
        }

        boolean removed = target.remove(animal);

        if (!removed) {
            LOGGER.warning("Animal could not be removed during undo: " + animal.name());
        }

        return removed;
    }

    @Override
    public String description() {
        return "Add animal: " + animal.name();
    }
}