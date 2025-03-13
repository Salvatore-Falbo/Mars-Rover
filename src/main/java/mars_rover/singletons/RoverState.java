package mars_rover.singletons;

import mars_rover.dtos.Position;
import mars_rover.exceptions.AlreadyInitializedException;

public class RoverState {
    private static Position position = null;

    public static Position getPosition() {
        return position;
    }

    public static void initialize(Position position) throws AlreadyInitializedException {
        if (RoverState.position!=null) {
            throw new AlreadyInitializedException();
        }
        RoverState.position = position;
    }

    public static double getAngle() {
        return RoverState.getPosition().getDirection().getAngle();
    }
}
