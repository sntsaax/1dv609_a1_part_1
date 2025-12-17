package com.lab;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordTest {
    private IPassword getPassword(String s) throws Exception {
        // Uncomment the version you are currently testing
        // return (IPassword) new Password(s);
        // return (IPassword) new BugDoesNotTrim(s);
        // return (IPassword) new BugToShortPassword(s);
        // return (IPassword) new BugVeryShort(s);
        // return (IPassword) new BugWrongExceptionMessage(s);
        // return (IPassword) new BugMissingPasswordLengthCheck(s);
        // return (IPassword) new BugMissingNumberCheck(s);
        // return (IPassword) new BugIsPasswordSameAlwaysTrue(s);
        // return (IPassword) new BugWrongHashingAlgorithm(s);
        return (IPassword) new BuggyPassword(s);
    }

    @Test
    public void testTrimBug() {
        // Targets: BugDoesNotTrim
        // "  short123  " is 12 chars untrimmed, but 8 chars trimmed. 
        // Buggy version won't trim and will incorrectly let this pass.
        assertThrows(Exception.class, () -> getPassword("  short123  "));
    }

    @Test
    public void testLengthBoundaries() {
        // Targets: BugToShortPassword, BugVeryShort, BugMissingPasswordLengthCheck
        // 11 characters + a number. The most rigorous check for "too short".
        assertThrows(Exception.class, () -> getPassword("LongEnoug11"));
    }

    @Test
    public void testExceptionMessage() {
        // Targets: BugWrongExceptionMessage
        // Specifically checks that the "referee" says the right words.
        Exception ex = assertThrows(Exception.class, () -> getPassword("abc1"));
        assertEquals("To short password", ex.getMessage());
    }

    @Test
    public void testMissingNumberBug() {
        // Targets: BugMissingNumberCheck
        // Long enough (12 chars), but no numbers.
        assertThrows(Exception.class, () -> getPassword("TwelveLetter"));
    }

    @Test
    public void testComparisonBug() throws Exception {
        // Targets: BugIsPasswordSameAlwaysTrue
        IPassword p1 = getPassword("ValidPassword123");
        IPassword p2 = getPassword("OtherPassword456");
        assertFalse(p1.isPasswordSame(p2), "Different passwords should not be the same!");
    }

    @Test
    public void testHashCalculationBug() throws Exception {
        // Targets: BugWrongHashingAlgorithm
        IPassword p = getPassword("ValidPass1234");
        // Correct hash for this string using the *31 multiplier algorithm
        int expectedHash = 1895687976; 
        assertEquals(expectedHash, p.getPasswordHash());
    }
}