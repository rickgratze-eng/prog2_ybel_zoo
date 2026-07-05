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

    public void executeCommand(Command<T> command, T target) {
        LOGGER.info("executeCommand: " + command.description());

        Result<ZooError, String> result = command.execute(target);

        switch (result) {
            case Result.Ok<ZooError, String> ok -> {
                LOGGER.info(ok.value());
                undoStack.push(command);
                redoStack.clear();
            }
            case Result.Err<ZooError, String> err -> {
                LOGGER.warning(err.error().toString());
            }
        }
    }

    public void undo(T target) {
        LOGGER.info("undo()");

        if (undoStack.isEmpty()) {
            LOGGER.warning(ZooError.UNDO_NOT_POSSIBLE.toString());
            return;
        }

        Command<T> command = undoStack.pop();
        Result<ZooError, String> result = command.undo(target);

        switch (result) {
            case Result.Ok<ZooError, String> ok -> {
                LOGGER.info(ok.value());
                redoStack.push(command);
            }
            case Result.Err<ZooError, String> err -> {
                LOGGER.warning(err.error().toString());
            }
        }
    }

    public void redo(T target) {
        LOGGER.info("redo()");

        if (redoStack.isEmpty()) {
            LOGGER.warning(ZooError.REDO_NOT_POSSIBLE.toString());
            return;
        }

        Command<T> command = redoStack.pop();
        Result<ZooError, String> result = command.execute(target);

        switch (result) {
            case Result.Ok<ZooError, String> ok -> {
                LOGGER.info(ok.value());
                undoStack.push(command);
            }
            case Result.Err<ZooError, String> err -> {
                LOGGER.warning(err.error().toString());
            }
        }
    }
}