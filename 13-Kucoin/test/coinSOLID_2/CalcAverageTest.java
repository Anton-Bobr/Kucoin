package coinSOLID_2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalcAverageTest {

    @Test
    void getAverage() {
        double [] [] input = {{9,8}, {11,12}, {9.5, 10.5}};
        CalcAverage calcAverage = new CalcAverage(input);
        double actual = calcAverage.getAverage();
        double expected = 10;
        assertEquals(expected,actual);
    }
}