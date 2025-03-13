package mars_rover.exceptions;

public class InvalidSequenceException extends Exception {
    public InvalidSequenceException() {
        super("Sequence must be declared");
    }
}
