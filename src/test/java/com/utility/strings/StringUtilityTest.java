package com.utility.strings;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilityTest {
    final String signStr = "+";

    long amount = 33806L;
    long hashAmount = 67612L;

    double amtDbl = 33806.10;
    double hashAmountDbl = 67612.10;


    String amountStr = String.valueOf(amount);
    String hashAmountStr = String.valueOf(hashAmount);

    int whiteSpaceLength = "       ".length();
    int zeroSpaceLength = "0000000".length();

    String leftZeroPadAmtStr = "       33806";
    String rightZeroPadAmtStr = "33806       ";
    String amtStr = "000000033806";

    String leftZeroPadHashAmtStr = "         67612";
    String rightZeroPadHashAmtStr = "67612         ";
    String hashAmtStr = "00000000067612";


    final String formattedAmtStr = amtStr + signStr;
    final String formattedAmtDblStr =  "000033806.10" + signStr;
    final String formattedHashAmtStr =  hashAmtStr + signStr;
    final String formattedHashAmtDblStr =  "00000067612.10" + signStr;

    final String fullAmountStr = formattedAmtStr + formattedHashAmtStr;


    @Test
    void testLeftPad1() {
        String testVal =  StringUtility.leftPad(whiteSpaceLength) + amountStr;
        assertEquals(leftZeroPadAmtStr, testVal);
    }

    @Test
    void testLeftPad2() {
        String testVal = StringUtility.leftPad(amountStr, amountStr.length() + whiteSpaceLength);
        assertEquals(leftZeroPadAmtStr, testVal);
    }

    @Test
    void testLeftPad3() {
        String testVal = StringUtility.leftPad(amountStr, zeroSpaceLength, StringUtility.ZERO_CHAR);
        assertEquals(amtStr, testVal);
    }


    @Test
    void testRightPad1() {
        String testVal = amountStr + StringUtility.rightPad(whiteSpaceLength);
        assertEquals(rightZeroPadAmtStr, testVal);
    }

    @Test
    void testRightPad2() {
        String testVal = StringUtility.rightPad(amountStr, amountStr.length() + whiteSpaceLength);
        assertEquals(rightZeroPadAmtStr, testVal);
    }

    @Test
    void testRightPad3() {
        String testVal = StringUtility.rightPad(amountStr, amountStr.length() + whiteSpaceLength, StringUtility.SPACE_CHAR);
        assertEquals(rightZeroPadAmtStr, testVal);
    }

    @Test
    void getNextSequenceString() {
        fail("implement test");
    }

    @Test
    void getDate() {
        fail("implement test");
    }

    @Test
    void getAmount() {
        assertEquals(StringUtility.getAmount(amount), formattedAmtStr);
    }

    @Test
    void getHashAmount() {
        assertEquals(StringUtility.getHashAmount(hashAmount), formattedHashAmtStr);
    }

    @Test
    void testGetAmountDouble() {
        assertEquals(StringUtility.getAmount(amtDbl), formattedAmtDblStr);
    }

    @Test
    void testGetHashAmount() {
        assertEquals(StringUtility.getHashAmount(hashAmountDbl), formattedHashAmtDblStr);
    }

    @Test
    void getFormattedAddress() {
        List<String> addrList = Arrays.asList("34 Road St",
                "P.O. Box 23",
                "Rockham",
                "Jones County",
                "QC",
                "Q32 5L6");

       String frmtAddr = StringUtility.getFormattedAddress(addrList);

    }

    @Test
    void testGetFormattedAddress() {
        fail("implement test");
    }

    @Test
    void getFullPaddedRecord() {
        fail("implement test");
    }
}