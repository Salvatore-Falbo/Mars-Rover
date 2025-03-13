package mars_rover.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import mars_rover.Constants;
import mars_rover.enums.DirectionEnum;

public class Position {
    @Min(value = 0)
    @Max(value = Constants.GRID_SIZE-1, message = "Max position is "+(Constants.GRID_SIZE-1))
    @NotNull
    private Integer x;

    @Min(value = 0)
    @Max(value = Constants.GRID_SIZE-1, message = "Max position is "+(Constants.GRID_SIZE-1))
    @NotNull
    private Integer y;

    @NotNull
    private DirectionEnum direction;

    public Position(Integer x, Integer y, DirectionEnum direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public DirectionEnum getDirection() {
        return direction;
    }

    public void setDirection(DirectionEnum direction) {
        this.direction = direction;
    }
}
