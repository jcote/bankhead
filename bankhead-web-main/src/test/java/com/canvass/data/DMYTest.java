package com.bankhead.data;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created By: jordancote
 * Created On: 12/8/13
 */

@RunWith(JUnit4.class)
public class DMYTest {

    @Test
    public void basicLow() throws InvalidDateException {
        String dobString = String.valueOf(18010101);
        DMY dmy = new DMY(dobString);
        Assert.assertEquals(1801, dmy.year);
        Assert.assertEquals(01, dmy.month);
        Assert.assertEquals(01, dmy.day);
    }

    @Test
    public void basicHigh() throws InvalidDateException {
        DMY dmy = new DMY(20121231);
        Assert.assertEquals(2012, dmy.year);
        Assert.assertEquals(12, dmy.month);
        Assert.assertEquals(31, dmy.day);
    }

    @Test(expected = InvalidDateException.class)
    public void invalidDayForMonth() throws InvalidDateException {
        DMY dmy = new DMY(20000230);
    }

    @Test(expected = InvalidDateException.class)
    public void shortNumber() throws InvalidDateException {
        DMY dmy = new DMY(12345);
    }

    @Test(expected = InvalidDateException.class)
    public void longNumber() throws InvalidDateException {
        DMY dmy = new DMY(1234234123);
    }

    @Test(expected = InvalidDateException.class)
    public void zero() throws InvalidDateException {
        DMY dmy = new DMY(0);
    }

    @Test(expected = InvalidDateException.class)
    public void negative() throws InvalidDateException {
        DMY dmy = new DMY(-19820305);
    }
}
