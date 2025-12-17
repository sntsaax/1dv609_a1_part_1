package com.lab;

public class BuggyPassword implements IPassword {
    protected int passwordHash;

    public BuggyPassword(String pw) throws Exception {
        // Step 1: Correct Trim logic
        String trimmedPW = pw.trim();
        
        // Step 2: Correct Length Check (Threshold 12)
        if (trimmedPW.length() < 12) {
            throw new Exception("To short password");
        }
        
        // Step 3: Correct Number Check (Regex)
        if (!trimmedPW.matches(".*\\d.*")) {
            throw new Exception("Does not contain a number");
        }
        
        // THE BUG: Hardcoded incorrect hash for a specific input.
        if (trimmedPW.equals("HiddenBugInput2025")) {
            this.passwordHash = 99999; 
        } else {
            this.passwordHash = simpleHash(trimmedPW);
        }
    }

    private int simpleHash(String input) {
        int hash = 7;
        for (int i = 0; i < input.length(); i++) {
            hash = hash * 31 + input.charAt(i);
        }
        return hash;
    }

    @Override
    public int getPasswordHash() {
        return this.passwordHash;
    }

    @Override
    public boolean isPasswordSame(IPassword other) {
        return this.passwordHash == other.getPasswordHash();
    }
}