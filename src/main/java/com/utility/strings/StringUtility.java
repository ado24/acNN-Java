package com.utility.strings;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

public class StringUtility {
    public static final String EMPTY_STRING = "";
    public static final char ZERO_CHAR = '0';
    public static final char SPACE_CHAR = ' ';
    public static final String RECORD_30 = "30";
    public static final int SEQUENCE_LENGTH = 10;
    public static final char NEWLINE = '\n';
    public static final char CARRIAGERETURN = '\r';
    public static final String CRLF = "\r\n";

    public static final int ADDRESS_LENGTH = 176;
    public static final int RECORD_LENGTH = 340;

    MessageFormat formatter;

    public static String leftPad(String input, int size, char padChar) {
        if (padChar == SPACE_CHAR)
            return String.format("%1$" + size + "s", input);
        return String.format("%s%s",
                String.format("%1$" + size + "s", SPACE_CHAR).replace(SPACE_CHAR, padChar),
                input);
    }

    public static String leftPad(String input, int size) {
        return leftPad(input, size, SPACE_CHAR);
    }

    public static String leftPad(int size) {
        return leftPad(EMPTY_STRING, size);
    }

    public static String rightPad(String input, int size, char padChar) {
        if (padChar == SPACE_CHAR)
            return String.format("%1$-" + size + "s", input);
        return String.format( "%s%s",
                input,
                String.format("%1$-" + size + "s", SPACE_CHAR).replace(SPACE_CHAR, padChar)
        );
    }

    public static String rightPad(String input, int size) {
        return rightPad(input, size, SPACE_CHAR);
    }

    public static String rightPad(int size) {
        return rightPad(EMPTY_STRING, size);
    }


//    public static void main(String[] args) {
//        Integer seqNum = 1;
//        StringUtility util = new StringUtility();
//        Date curDate = new Date();
//
//        //Example used for calculating current lines of amount and running total
//        Long runningTotal = 0l;
//        Double amt = 338.06; //Double amount
//
//        Long amtInt = (long) (amt * 100); //Integer amount
//
//        runningTotal += amtInt; //Updating accumulator
//        long amtHash = runningTotal;
//
//        //String version of expected 'amt'
//        String amtStrCmp = "000000033806+";
//
//
//        //Test object record array for Message Formatter
//        //Address test
//        List<String> addrList = List.of("34 Road St",
//                "P.O. Box 23",
//                "Rockham",
//                "Jones County",
//                "QC",
//                "Q32 5L6");
//
//        Object[] recs = {RECORD_30,
//                getNextSequenceString(seqNum),
//                rightPad(5).intern(),
//                1,
//                getDate(curDate), leftPad(10).intern(),
//                leftPad("324235236",10),
//                rightPad("JOHN DOE" ,5),
//                rightPad("1200000124",5),
//                leftPad("349", 10),
//                rightPad(getFormattedAddress(addrList),30),
//                getAmount(amtInt),
//                leftPad(getHashAmount((long) (amtInt + amtHash)),10),
//                rightPad(10),
//                "CAD",
//                "CA", 0};
//
//
//        List<Object> recsList = Arrays.asList(recs);
//        Map<Integer, List<Object>> recsMap = new java.util.HashMap<>();
//
//
//        /** Implementation ideas **/
//
//
//        /** Build a record with a Message Formatter and String concats **/
//        String formattedConcat = MessageFormat.format(
//                "{0}{1}{2}{3}{4}{5}",
//                recs);
//
//        //Output concat version
//        System.out.println(formattedConcat);
//
//        /** Next version **/
//
//        /* Build a record with a Message Formatter and String Builder */
//        StringBuilder sb = new StringBuilder(710);
//        for (int i = 0; i < recs.length; i++) {
//            sb.append(String.format("{%d}", i));
//        }
//
//
//
//        System.out.println(util.rightPad(getFormattedAddress(addrList), 45));
//
//        System.out.println("Next");
//        //Increment sequence
//        recsList.set(1, util.getNextSequenceString(++seqNum));
//        //recsList.add(String.format("%012d+%014d+1", amtInt, amtHash));
//        //Output SB version
//        System.out.println("Message Format version");
//        System.out.println(getFullPaddedRecord(MessageFormat.format(sb.toString().intern(), recsList.toArray())).substring(0, RECORD_LENGTH));
//        System.out.println();
//        for (int i = 0; i < recs.length; i++) {
//            recsList.set(1, util.getNextSequenceString(++seqNum));
//            recsList.set(recsList.size() - 1, getHashAmount((long) (amtInt + amtHash)));
//            recsMap.put(i, recsList);
//        }
////        System.out.println("Map version");
////        System.out.println(MessageFormat.format(sb.toString().intern(), recsList.toArray()));
//
//        //Insert test
//        /*sb.insert(10, util.getNextSequenceString(++seqNum), 0, 12 );
//        sb.insert(30, String.format("%s%s", getDate(curDate), util.rightPad(10)).intern(), 0, String.format("%1$14tY%tm%td%s", curDate, curDate, util.rightPad(10)).length() );
//        System.out.println("MF with inserts");
//        System.out.println(MessageFormat.format(sb.toString().intern(),recs));
//*/
//        /** Next version **/
//
//        //Format sequence number, with 10 leading 0's
//        System.out.printf("%s%010d", RECORD_30, getNextSequence(seqNum++));
//
//        //Format DateTime with 14 leading spaces, and 10 right padded spaces afterwards
//        System.out.printf("%1$14tY%tm%td%s", curDate, curDate, util.rightPad(10));
//
//        //Format current record's payment amount, suffixed with the running total amount (returned as summation hash code for a boxed Integer)
//        System.out.printf("%012d+%014d+1", amtInt, amtHash);
//        System.out.println();
//
//        List<Object> testVals = Arrays.asList(RECORD_30, getNextSequence(seqNum++),curDate,curDate, util.rightPad(10),  amtInt, amtHash);
//        //RecordType,SeqNum,YearMonth,DayOfYear,padding,PayAmt,HashRunningTotal with newline
//        List<String> testPattern = Arrays.asList("%s", "%010d", "%1$14tY%tm","%td","%s", "%012d+", "%014d+1\n");
//
//        Map<String, Object> testPatternMap = new HashMap<String, Object>();
//        for (int i = 0; i < testVals.size(); i++) {
//            testPatternMap.put(testPattern.get(i), testVals.get(i));
//        }
//        System.out.println("Printf version with list");
//        for (int i = 0; i < testPattern.size(); i++) {
//            System.out.printf(testPattern.get(i), testVals.get(i));
//        }
//
//        System.out.println("Printf with arrays");
//        //Version of above using arrays
//        Object[] testValArr = {RECORD_30, getNextSequenceString(seqNum++),getDate(curDate), util.rightPad(10),  getAmount(amtInt),getHashAmount(amtHash)};
//        //RecordType,SeqNum,YearMonth,DayOfYear,padding,PayAmt with framing +,HashRunningTotal with framing+ newline
//        String[] testPatternArr = {"%s", "%s", "%s", "%s", "%s", "%s+1\n"};
//
//        testPatternMap.clear();
//        testPatternMap = new HashMap<String, Object>();
//
//        for (int i = 0; i < testValArr.length; i++) {
//            testPatternMap.put(testPatternArr[i], testValArr[i]);
//        }
//        System.out.println("Printf version with arrays");
//        //Output with
//        for (int i = 0; i < testPatternArr.length; i++) {
//            System.out.printf(testPatternArr[i], testValArr[i]);
//        }
//
//    }

    private static Integer getNextSequence(Integer seqNum) {
        //++seqNum;
        return Integer.valueOf(++seqNum);
    }

    public static String getNextSequenceString(Integer seqNum) {
        return leftPad(seqNum.toString(), SEQUENCE_LENGTH, ZERO_CHAR);
    }

    public static String getDate(Date date) {
        return  String.format("%1$tY%1$tm%1$td", date);
    }

    public static String getAmount(Double amt) {
        return String.format("%012.2f+", amt);
    }

    public static String getHashAmount(Double amt) {
        return String.format("%014.2f+", amt);
    }

    public static String getAmount(Long amt) {
        return String.format("%012d+", amt);
    }

    public static String getHashAmount(Long amt) {
        return String.format("%014d+", amt);
    }

    public static String getFormattedAddress(String... entries) {
        return getFormattedAddress(List.of(entries));
    }
    
    public static String getFormattedAddress(List<String> entries) {
        StringBuilder formatter = new StringBuilder(entries.size());
        for (String curEntry : entries) {
            formatter.append(rightPad(curEntry, curEntry.length() + 1, SPACE_CHAR));
        }
        if (formatter.length() > ADDRESS_LENGTH)
            return rightPad(formatter.toString(), ADDRESS_LENGTH, SPACE_CHAR).substring(0, ADDRESS_LENGTH);
        return rightPad(formatter.toString(), ADDRESS_LENGTH, SPACE_CHAR);
    }

    public static String getFullPaddedRecord(String record) {
        return rightPad(record, RECORD_LENGTH, SPACE_CHAR);
    }
}
