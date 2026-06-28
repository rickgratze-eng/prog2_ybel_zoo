package zoo.command;

import zoo.animal.Animal;
import zoo.enclosure.Enclosure;

import java.util.logging.Logger;

public class RemoveAnimalCommand<T extends Animal> implements Command<Enclosure<? super T>> {

    private static final Logger LOGGER = Logger.getLogger(RemoveAnimalCommand.class.getName());

    private final T animal;
    private boolean executed;

    public RemoveAnimalCommand(T animal) {
        this.animal = animal;
        this.executed = false;
    }

    @Override
    public boolean execute(Enclosure<? super T> target) {
        boolean removed = target.remove(animal);

        if (!removed) {
            LOGGER.warning("Animal could not be removed: " + animal.name());
        }

        executed = removed;
        return removed;
    }

    @Override
    public boolean undo(Enclosure<? super T> target) {
        if (!executed) {
            LOGGER.warning("Undo not possible before command was executed.");
            return false;
        }

        boolean added = target.add(animal);

        if (!added) {
            LOGGER.warning("Animal could not be added during undo: " + animal.name());
        }

        return added;
    }

    @Override
    public String description() {
        return "Remove animal: " + animal.name();
    }
}