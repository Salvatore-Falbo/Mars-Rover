package mars_rover.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DirectionEnum {
    EAST('E'),
    NORTH('N'),
    WEST('W'),
    SOUTH('S');

    private final char direction;

    DirectionEnum(char direction) {
        this.direction = direction;
    }

    @JsonValue
    public char getDirection() {
        return direction;
    }

    public double getAngle() {
        return this.ordinal()*Math.PI*2/DirectionEnum.values().length;
    }

    public static DirectionEnum valueOf(char direction) {
        for (DirectionEnum d : DirectionEnum.values()) {
            if (d.getDirection() == direction) {
                return d;
            }
        }

        throw new IllegalArgumentException("Invalid direction: " + direction);
    }
}
