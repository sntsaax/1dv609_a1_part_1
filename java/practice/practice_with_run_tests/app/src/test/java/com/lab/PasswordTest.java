package com.lab;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Password implementations.
 * 
 * To test different buggy versions, simply uncomment the corresponding
 * getPassword() method and comment out the others.
 * 
 * Available implementations:
 * - Password: Correct implementation
 * - BugDoesNotTrim: Does not trim whitespace
 * - BugToShortPassword: Allows passwords shorter than 12 characters
 * - BugVeryShort: Allows way to short passwords
 * - BugWrongExceptionMessage: Wrong exception message for short passwords
 * - BugMissingPasswordLengthCheck: Does not throw exception for short passwords
 * - BugMissingNumberCheck: Does not throw exception if password lacks a number
 * - BugIsPasswordSameAlwaysTrue: isPasswordSame always returns true
 * - BugWrongHashingAlgorithm: Wrong hashing algorithm
 */

public class PasswordTest {
    private IPassword getPassword(String s) throws Exception {
        // return (IPassword) new Password(s);
        // return (IPassword) new BugDoesNotTrim(s);
        // return (IPassword) new BugToShortPassword(s);
        // return (IPassword) new BugToShortPassword(s);
        // return (IPassword) new BugVeryShort(s);
        // return (IPassword) new BugWrongExceptionMessage(s);
        return (IPassword) new BugMissingPasswordLengthCheck(s);
        // return (IPassword) new BugMissingNumberCheck(s);
        // return (IPassword) new BugIsPasswordSameAlwaysTrue(s);
        // return (IPassword) new BugWrongHashingAlgorithm(s);
    }

    @Test
    public void constructorShouldThrowExceptionForUntrimmedShortPassword() {
        /* Input String: "  short123  " 
        - Untrimmed length: 12 characters
        - Trimmed length: 8 characters
        */
        String input = "  short123  ";

        // The correct Password should throw an exception
        assertThrows(Exception.class, () -> {
            getPassword(input);
        }, "The password was too short when trimmed, but no exception was thrown!");
    }

    @Test
    public void constructorShouldThrowExceptionForPasswordLength11() {
        // Input string: Exactly 11 characters + a number
        // "LongEnoug11" is 11 chars. 
        
        String input = "LongEnoug11"; 

        assertThrows(Exception.class, () -> {
            getPassword(input);
        }, "Failed to catch a password that was 11 characters long!");
    }

    @Test
    public void constructorShouldThrowExceptionForPasswordLength6() {
        /* Input String: "Passw1" (Length: 6)
        - Correct Version: Sees length 6. 6 < 12 is TRUE. Throws exception.
        - BugVeryShort: Sees length 6. 6 < 6 is FALSE. Does NOT throw exception.
        */
        String input = "Passw1"; 

        assertThrows(Exception.class, () -> {
            getPassword(input);
        }, "BugVeryShort allowed a 6-character password to pass!");
    }

    @Test
    public void constructorShouldThrowCorrectMessageForShortPassword() {
        /* Input String: "abc1" (4 characters)
        */
        String input = "abc1";

        // 1. Capture the exception object in a variable
        Exception exception = assertThrows(Exception.class, () -> {
            getPassword(input);
        });

        // 2. Assert that the message is EXACTLY what is required
        // Expected: "To short password"
        // Actual (in this bug): "Wrong message"
        assertEquals("To short password", exception.getMessage(), 
            "The exception was thrown, but the message text was incorrect!");
    }

    @Test
    public void constructorShouldThrowExceptionForShortPasswordWithNumber() {
        /* Input String: "1" (Length: 1)
        - It is shorter than 12.
        - It contains a number ('1').
        */
        String input = "1";

        // Correct version should blow the whistle because it's too short.
        assertThrows(Exception.class, () -> {
            getPassword(input);
        }, "The code allowed a 1-character password to pass because it forgot the length check!");
    }
}
