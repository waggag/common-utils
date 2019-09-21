package cn.waggag.file.ffmpeg;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class VideoUtilsTest {

    private VideoUtils videoUtils = new VideoUtils("D:\\Java\\ffmpeg\\bin\\ffmpeg.exe");

    @Test
    public void checkVideoTime() {
        Boolean videoTime = videoUtils.checkVideoTime("D:\\develop\\lucene.avi", "D:\\develop\\lucene2.avi");
        System.out.println(videoTime);
        Assert.assertTrue(videoTime);
    }

    @Test
    public void getVideoTime() {
        String videoTime = videoUtils.getVideoTime("D:\\develop\\lucene.avi");
        String videoTime1 = videoUtils.getVideoTime("D:\\develop\\lucene2.avi");
        Assert.assertEquals(videoTime,videoTime1);
    }

    @Test
    public void waitFor() {
        ProcessBuilder builder = new ProcessBuilder();
        Process process = null;
        try {
            builder.command("ipconfig");
            process = builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String waitFor = videoUtils.waitFor(process);
        System.out.println(waitFor);
    }
}
