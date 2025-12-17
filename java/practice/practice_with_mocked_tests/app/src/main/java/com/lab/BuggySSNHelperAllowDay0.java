package com.lab;

public class BuggySSNHelperAllowDay0 extends SSNHelper {
    @Override
    public boolean isValidDay(String day) {
        try {
            int d = Integer.parseInt(day);
            // BUG: Allowing 0 makes extra test FAIL
            return d >= 0 && d <= 31; 
        } catch (NumberFormatException e) {
            return false;
        }
    }
}