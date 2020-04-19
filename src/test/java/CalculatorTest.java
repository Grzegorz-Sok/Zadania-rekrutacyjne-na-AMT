
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    @Test
    public void calculatorTest(){
        Calculator calculatorTester = new Calculator();
        List<Double> list = new ArrayList<>();
        list.add(2d);
        list.add(3d);
        list.add(1d);
        list.add(4d);
        assertEquals("2,50", calculatorTester.average(list), "Average should equal 2,50");
        assertEquals("2,50", calculatorTester.median(list), "Median should equal 2,50");
        assertEquals("1,00", calculatorTester.min(list), "Min should equal 1,00");
        assertEquals("4,00", calculatorTester.max(list), "Max should equal 4,00");
        assertEquals("1,12", calculatorTester.standardDeviation(list), "Standard deviation should equal 1,12");
        list.add(0d);
        assertEquals("2,00", calculatorTester.median(list), "Median should equal 2,00");
    }
}
