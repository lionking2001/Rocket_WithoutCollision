package game;

/**
 * Created by Dto on 9/15/2016.
 */

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public enum Direction {
    UP(1, 1.5707963267948966),
    DOWN(5, -1.5707963267948966),
    LEFT(7, -3.141592653589793),
    RIGHT(3, 0.0),
    UP_LEFT(8, 2.356194490192345),
    UP_RIGHT(2, 0.7853981633974483),
    DOWN_LEFT(6, -2.356194490192345),
    DOWN_RIGHT(4, -0.7853981633974483);

    private static final Map<Integer, Direction> map;
    private int value;
    private double radian;

    public static Direction valueOf(int value) {
        return map.get(value);
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public double getRadian() {
        return this.radian;
    }

    public void setRadian(double radian) {
        this.radian = radian;
    }

    private Direction(int value, double radian) {
        this.value = value;
        this.radian = radian;
    }

    static {
        map = Arrays.stream(Direction.values()).collect(Collectors.toMap(direction -> direction.value, direction -> direction));
    }
}
