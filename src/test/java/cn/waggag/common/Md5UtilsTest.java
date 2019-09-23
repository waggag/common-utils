package cn.waggag.common;

import org.junit.Test;

public class Md5UtilsTest {

    @Test
    public void getHash() {
        String hash = Md5Utils.getHash("225514");
        System.out.println(hash);
    }
}
