package mars_rover.implementation;

import mars_rover.enums.DirectionEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectionEnumTest {
    @Test
    void test_all_angles() {
        assertEquals(Math.PI/2, DirectionEnum.NORTH.getAngle());
        assertEquals(Math.PI/2*3, DirectionEnum.SOUTH.getAngle());
        assertEquals(Math.PI, DirectionEnum.WEST.getAngle());
        assertEquals(0, DirectionEnum.EAST.getAngle());
    }
}
