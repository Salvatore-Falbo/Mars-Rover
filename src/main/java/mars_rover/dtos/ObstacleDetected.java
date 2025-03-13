package mars_rover.dtos;

public class ObstacleDetected {
    private final Position roverPosition;
    private final int obstacleX;
    private final int obstacleY;

    public ObstacleDetected(Position roverPosition, int obstacleX, int obstacleY) {
        this.roverPosition = roverPosition;
        this.obstacleX = obstacleX;
        this.obstacleY = obstacleY;
    }

    public Position getRoverPosition() {
        return roverPosition;
    }

    public int getObstacleX() {
        return obstacleX;
    }

    public int getObstacleY() {
        return obstacleY;
    }
}
