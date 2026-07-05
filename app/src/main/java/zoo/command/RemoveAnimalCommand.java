package zoo.command;

import zoo.animal.Animal;
import zoo.enclosure.Enclosure;

public class RemoveAnimalCommand<T extends Animal> implements Command<Enclosure<? super T>> {

    private final T animal;
    private boolean executed;

    public RemoveAnimalCommand(T animal) {
        this.animal = animal;
        this.executed = false;
    }

    @Override
    public Result<ZooError, String> execute(Enclosure<? super T> target) {
        boolean removed = target.remove(animal);

        if (!removed) {
            return Result.err(ZooError.ANIMAL_NOT_FOUND);
        }

        executed = true;
        return Result.ok("Removed animal: " + animal.name());
    }

    @Override
    public Result<ZooError, String> undo(Enclosure<? super T> target) {
        if (!executed) {
            return Result.err(ZooError.UNDO_NOT_POSSIBLE);
        }

        boolean added = target.add(animal);

        if (!added) {
            return Result.err(ZooError.ANIMAL_ALREADY_EXISTS);
        }

        executed = false;
        return Result.ok("Undo remove animal: " + animal.name());
    }

    @Override
    public String description() {
        return "Remove animal: " + animal.name();
    }
}