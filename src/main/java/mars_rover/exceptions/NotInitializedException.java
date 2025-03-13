package mars_rover.exceptions;

public class NotInitializedException extends Exception {
    public NotInitializedException() {
        super("Rover is not initialized");
    }
}
