package mars_rover.commands;

import mars_rover.enums.CommandEnum;

public class CommandFactory {
    public static CommandInterface getCommand(CommandEnum command) {
        return switch (command) {
            case FORWARD -> new ForwardCommand();
            case BACKWARD -> new BackwardCommand();
            case LEFT -> new LeftCommand();
            case RIGHT -> new RightCommand();
        };
    }
}
