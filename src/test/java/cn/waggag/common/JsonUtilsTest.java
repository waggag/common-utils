package cn.waggag.common;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class JsonUtilsTest {

    @Test
    public void serialize() {
        Date date = new Date();
        String jsonDate = JsonUtils.serialize(date);
        Assert.assertEquals(jsonDate.getClass(), String.class);
    }

    @Test
    public void parse() {
        String json = "1567099724758";
        Date date = JsonUtils.parse(json, Date.class);
        Assert.assertEquals(Date.class,date.getClass());
    }

    @Test
    public void parseList() {
    }

    @Test
    public void parseMap() {
    }

    @Test
    public void nativeRead() {
    }
}
