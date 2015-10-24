package com.bankhead.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created By: jordancote
 * Created On: 12/8/13
 */
public class DMY {
    public int day;
    public int month;
    public int year;

    public static boolean checkValidDMY(String dobString) throws InvalidDateException {
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        formatter.setLenient(false);
        try {
            formatter.parse(dobString);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public DMY(int dob) throws InvalidDateException {
        this(String.valueOf(dob));
    }

    public DMY(String dobString) throws InvalidDateException {
        if (!checkValidDMY(dobString)) {
            throw new InvalidDateException("Stored dateOfBirth " + dobString + " is not of format yyyyMMdd");
        }
        int dob;
        try {
            dob = Integer.parseInt(dobString);
        } catch (NumberFormatException e) {
            throw new InvalidDateException(dobString + " could not be converted to int");
        }

        this.year = Integer.valueOf(dobString.substring(0,4));
        this.month = Integer.valueOf(dobString.substring(4, 6));
        this.day = Integer.valueOf(dobString.substring(6, 8));
    }
}
