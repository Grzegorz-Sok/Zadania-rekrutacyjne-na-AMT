import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormatterTest {

    @Test
    public void formatterTest(){
        String test = "One \"does\" not simply do tests, 1 12, 1\\u00a01";
        String[] result = {"One does not simply do tests, 112, 11"};
        Formatter.dataFormat(test);
        assertEquals("One does not simply do tests, 112, 11", result[0]);
    }
}
