package cn.waggag.file;

import org.junit.Assert;
import org.junit.Test;


public class UpLoadUtilsTest {


    @Test
    public void getUUIDName() {
        String uuidName = UpLoadUtils.getUUIDName("waggag.jpg");
        System.out.println(uuidName);
    }

    @Test
    public void getRealName() {
        String realName = UpLoadUtils.getRealName("C:/hello.html");
        Assert.assertEquals("hello.html",realName);
        System.out.println(realName);
    }

    @Test
    public void getDir() {
        String dir = UpLoadUtils.getDir("hello.jpg");
        System.out.println(dir);
    }
}
