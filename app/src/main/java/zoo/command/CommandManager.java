package zoo.command;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Logger;

public class CommandManager<T> {

    private static final Logger LOGGER = Logger.getLogger(CommandManager.class.getName());

    private final Deque<Command<T>> undoStack;
    private final Deque<Command<T>> redoStack;

    public CommandManager() {
        this.undoStack = new ArrayDeque<>();
        this.redoStack = new ArrayDeque<>();
    }

    public boolean executeCommand(Command<T> command, T target) {
        LOGGER.info("executeCommand: " + command.description());

        boolean success = command.execute(target);

        if (success) {
            undoStack.push(command);
            redoStack.clear();
            LOGGER.fine("Command executed. Undo stack: " + undoStack.size()
                    + ", redo stack: " + redoStack.size());
        } else {
            LOGGER.warning("Command failed: " + command.description());
        }

        return success;
    }

    public boolean undo(T target) {
        LOGGER.info("undo()");

        if (undoStack.isEmpty()) {
            LOGGER.warning("Undo not possible: undo stack is empty.");
            return false;
        }

        Command<T> command = undoStack.pop();
        boolean success = command.undo(target);

        if (success) {
            redoStack.push(command);
            LOGGER.fine("Undo successful. Undo stack: " + undoStack.size()
                    + ", redo stack: " + redoStack.size());
        } else {
            LOGGER.warning("Undo failed: " + command.description());
        }

        return success;
    }

    public boolean redo(T target) {
        LOGGER.info("redo()");

        if (redoStack.isEmpty()) {
            LOGGER.warning("Redo not possible: redo stack is empty.");
            return false;
        }

        Command<T> command = redoStack.pop();
        boolean success = command.execute(target);

        if (success) {
            undoStack.push(command);
            LOGGER.fine("Redo successful. Undo stack: " + undoStack.size()
                    + ", redo stack: " + redoStack.size());
        } else {
            LOGGER.warning("Redo failed: " + command.description());
        }

        return success;
    }
}