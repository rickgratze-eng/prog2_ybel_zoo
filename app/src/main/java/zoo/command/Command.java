package zoo.command;

public interface Command<T> {

    Result<ZooError, String> execute(T target);

    Result<ZooError, String> undo(T target);

    String description();
}