package mars_rover.exceptions;

public class ObstacleDetectedException extends Exception {
    private final int obstacleX;
    private final int obstacleY;

    public ObstacleDetectedException(int obstacleX, int obstacleY) {
        super();
        this.obstacleX = obstacleX;
        this.obstacleY = obstacleY;
    }

    public int getObstacleX() {
        return obstacleX;
    }

    public int getObstacleY() {
        return obstacleY;
    }
}
