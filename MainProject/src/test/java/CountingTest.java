import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;


public class CountingTest {

    @ParameterizedTest
    @CsvSource({"3 + 5, 3 5 +", "4 - 5 * 34.5 / 7, 4 5 34.5 * 7 / -", 
    	"7 + 5.6 * 34 / 8 + ( 9 - 5 ), 7 5.6 34 * 8 / + 9 5 - +"})
    @DisplayName("create_RPN test with numbers")
    public void create_OPZTest(String initialExpression, String outputRPN) {
    	String createdRPN = Counting.create_OPZ(initialExpression);
        assertEquals(createdRPN, outputRPN);
    }	 

    @ParameterizedTest
    @CsvSource({"3 5 +, 8", "4 5 34.5 * 7 / -, -20.642857142857142", 
    	"7 5.6 34 * 8 / + 9 5 - +, 34.8"})
    @DisplayName("calculate_RPN test with numbers")
    public void calculate_OPZTest(String inputRPN, double ultimateResult) {
    	double derivedResult = Counting.calculate_OPZ(inputRPN);
        assertEquals(derivedResult, ultimateResult);
    }

//    @ParameterizedTest
//    @CsvSource({"123", "-123.45", "abc"})
//    @DisplayName("checking is the symbol a numeric")
    @Test
    public void isNumericTest() {
        assertTrue(Counting.isNumeric("123"));
        assertTrue(Counting.isNumeric("-123.45"));
        assertFalse(Counting.isNumeric("abc"));
    }

    @ParameterizedTest
    @CsvSource({"x, true", "varName, true", "123, false"})
    @DisplayName("checking is the symbol a variable")
    public void iVariableTest(String symbolFromString, boolean correctVerification) {
        boolean checkingByMethod = Counting.isVariable(symbolFromString);
        assertEquals(checkingByMethod, correctVerification);
        
    }

    @ParameterizedTest
    @CsvSource({"+, 1", "-, 1", "*, 2", "/, 2", "(, 0"})
    @DisplayName("determining priority tests")
    public void priorityTest(char symbolFromString, int correctPriority) {
    	int derivedPriority = Counting.priority(symbolFromString);
        assertEquals(derivedPriority, correctPriority);
    }
}
