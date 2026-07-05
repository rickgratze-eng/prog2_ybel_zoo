package zoo;

import org.junit.jupiter.api.Test;
import zoo.animal.Lion;
import zoo.command.AddAnimalCommand;
import zoo.command.CommandManager;
import zoo.enclosure.CatHouse;
import zoo.enclosure.Enclosure;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandManagerTest {

    @Test
    void testUndoRedo() {
        CatHouse<Lion> catHouse = new CatHouse<>("Cats");

        CommandManager<Enclosure<? super Lion>> manager = new CommandManager<>();

        AddAnimalCommand<Lion> command =
                new AddAnimalCommand<>(new Lion("Simba"));

        manager.executeCommand(command, catHouse);

        assertEquals(1, catHouse.getInhabitants().size());

        manager.undo(catHouse);

        assertEquals(0, catHouse.getInhabitants().size());

        manager.redo(catHouse);

        assertEquals(1, catHouse.getInhabitants().size());
    }
}