package com.lab;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SwedishSocialSecurityNumberTest {
    
    private SSNHelper mockHelper;
    
    @BeforeEach
    public void setUp() {
        // Goal: Isolate SwedishSocialSecurityNumber from real SSNHelper logic
        mockHelper = mock(SSNHelper.class);
    }
    
    @Test
    public void shouldAcceptValidSSN() throws Exception {
        
        when(mockHelper.isCorrectLength(anyString())).thenReturn(false);
        when(mockHelper.isCorrectFormat(anyString())).thenReturn(true);
        when(mockHelper.isValidMonth(anyString())).thenReturn(true);
        when(mockHelper.isValidDay(anyString())).thenReturn(true);
        when(mockHelper.luhnIsCorrect(anyString())).thenReturn(true);

        SwedishSocialSecurityNumber ssn = new SwedishSocialSecurityNumber("900101-0017", mockHelper);
        
        assertEquals("90", ssn.getYear());
        assertEquals("0017", ssn.getSerialNumber());
    }

    @Test
    public void shouldThrowExceptionWhenLengthCheckFails() {
        // TARGETS: BuggySwedishSocialSecurityNumberNoLenCheck
        // Force the helper to return false. 
        // If SSSN doesn't throw an exception, the SSSN class has the bug.
        when(mockHelper.isCorrectLength(anyString())).thenReturn(false);

        assertThrows(Exception.class, () -> {
            new SwedishSocialSecurityNumber("900101-0017", mockHelper);
        }, "SSSN should fail if the helper rejects the length");
    }
}