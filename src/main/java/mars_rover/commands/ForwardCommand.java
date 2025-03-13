package mars_rover.commands;

import mars_rover.Constants;
import mars_rover.exceptions.ObstacleDetectedException;
import mars_rover.singletons.GridState;
import mars_rover.singletons.RoverState;

public class ForwardCommand  implements CommandInterface {
    @Override
    public void execute() throws ObstacleDetectedException {
        //calculate next position
        int nextX = (RoverState.getPosition().getX() + Constants.GRID_SIZE + (int)Math.cos(RoverState.getAngle())) % Constants.GRID_SIZE;
        int nextY = (RoverState.getPosition().getY() + Constants.GRID_SIZE + (int)Math.sin(RoverState.getAngle())) % Constants.GRID_SIZE;

        if (GridState.isFree(nextX, nextY)) {
            RoverState.getPosition().setX(nextX);
            RoverState.getPosition().setY(nextY);
        }
        else {
            throw new ObstacleDetectedException(nextX, nextY);
        }
    }
}
