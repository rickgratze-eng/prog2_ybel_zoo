package zoo.command;

import zoo.animal.Animal;
import zoo.enclosure.Enclosure;

public class AddAnimalCommand<T extends Animal> implements Command<Enclosure<? super T>> {

    private final T animal;
    private boolean executed;

    public AddAnimalCommand(T animal) {
        this.animal = animal;
        this.executed = false;
    }

    @Override
    public Result<ZooError, String> execute(Enclosure<? super T> target) {
        boolean added = target.add(animal);

        if (!added) {
            return Result.err(ZooError.ANIMAL_ALREADY_EXISTS);
        }

        executed = true;
        return Result.ok("Added animal: " + animal.name());
    }

    @Override
    public Result<ZooError, String> undo(Enclosure<? super T> target) {
        if (!executed) {
            return Result.err(ZooError.UNDO_NOT_POSSIBLE);
        }

        boolean removed = target.remove(animal);

        if (!removed) {
            return Result.err(ZooError.ANIMAL_NOT_FOUND);
        }

        executed = false;
        return Result.ok("Undo add animal: " + animal.name());
    }

    @Override
    public String description() {
        return "Add animal: " + animal.name();
    }
}