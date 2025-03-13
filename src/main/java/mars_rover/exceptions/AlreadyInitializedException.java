package mars_rover.exceptions;

public class AlreadyInitializedException extends Exception {
    public AlreadyInitializedException() {
        super("Cannot initialize multiple times");
    }
}
