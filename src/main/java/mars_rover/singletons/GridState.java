package mars_rover.singletons;

import mars_rover.Constants;

public class GridState {

    //grid[x][y], point (0;0) is bottom-left corner
    private static boolean[][] grid = new boolean[Constants.GRID_SIZE][Constants.GRID_SIZE];

    public static void initialize(int startX, int startY) {
        for (int i = 0; i < Constants.GRID_SIZE; i++) {
            for (int j = 0; j < Constants.GRID_SIZE; j++) {
                grid[i][j] = Math.random() >= Constants.OBSTACLE_RATE;
            }
        };

        //starting point must be empty
        grid[startX][startY] = true;
    }

    public static boolean isFree(int x, int y) {
        return grid[x][y];
    }
}
