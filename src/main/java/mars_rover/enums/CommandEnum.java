package mars_rover.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import mars_rover.exceptions.UnsupportedCommandException;

public enum CommandEnum {
    FORWARD('f'),
    BACKWARD('b'),
    LEFT('l'),
    RIGHT('r');

    private final char command;

    CommandEnum(char command) {
        this.command = command;
    }

    @JsonValue
    public char getCommand() {
        return command;
    }

    public static CommandEnum valueOf(char command) throws UnsupportedCommandException {
        for (CommandEnum c : CommandEnum.values()) {
            if (c.getCommand() == command) {
                return c;
            }
        }

        throw new UnsupportedCommandException(command);
    }
}
