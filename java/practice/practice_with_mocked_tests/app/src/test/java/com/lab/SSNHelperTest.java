package com.lab;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class SSNHelperTest {
    
    private SSNHelper helper;
    
    @BeforeEach
    public void setUp() {
        helper = new SSNHelper();
        helper = new BuggySSNHelperAllowDay0(); 
    }
    
    @Test
    public void testIsCorrectLength() {
        // TARGETS: BuggySSNHelperWrongLength
        assertFalse(helper.isCorrectLength("900101"), "Should reject short strings");
        assertTrue(helper.isCorrectLength("900101-0017"), "Should accept 11 characters");
    }

    @Test
    public void testIsValidMonth() {
        // TARGETS: BuggySSNHelperAllowMonth0
        assertFalse(helper.isValidMonth("00"), "Month 00 should be invalid");
        assertTrue(helper.isValidMonth("01"), "Month 01 should be valid");
    }

    // ADDITIONAL TEST
    @Test
    public void testIsValidDayZero() {
        // VALUE: Adds a lower-boundary check for days.
        assertFalse(helper.isValidDay("00"), "Day 00 must be invalid");
    }
}