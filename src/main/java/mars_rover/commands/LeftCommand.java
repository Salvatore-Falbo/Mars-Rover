package mars_rover.commands;

import mars_rover.Constants;
import mars_rover.enums.DirectionEnum;
import mars_rover.exceptions.ObstacleDetectedException;
import mars_rover.singletons.GridState;
import mars_rover.singletons.RoverState;

public class LeftCommand implements CommandInterface {
    @Override
    public void execute() {
        int ordinal = RoverState.getPosition().getDirection().ordinal();
        DirectionEnum nextDirection = DirectionEnum.values()[(ordinal+1)%DirectionEnum.values().length];
        RoverState.getPosition().setDirection(nextDirection);
    }
}
