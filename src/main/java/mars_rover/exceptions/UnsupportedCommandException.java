package mars_rover.exceptions;

public class UnsupportedCommandException extends Exception {
    char command;

    public UnsupportedCommandException(char command) {
        super("Invalid command: " + command);
        this.command = command;
    }

    public char getCommand() {
        return command;
    }
}
