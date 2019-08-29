package cn.waggag.common;

import org.junit.Assert;
import org.junit.Test;

public class NumberUtilsTest {


    @Test
    public void generateCode() {
        for (int i = 0; i < 10; i++) {
            String generateCode = NumberUtils.generateVerifyCode(6);
            System.out.println(generateCode);
        }
    }

    @Test
    public void isInt() {
        Assert.assertEquals(false,NumberUtils.isInt(5.79));
        Assert.assertEquals(true,NumberUtils.isInt(5.00));
    }

    @Test
    public void isDigit() {
        Assert.assertEquals(false,NumberUtils.isDigit("123456abc"));
        Assert.assertEquals(true,NumberUtils.isDigit("123456"));
    }
}
