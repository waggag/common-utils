package cn.waggag.file.ffmpeg;

import org.junit.Assert;
import org.junit.Test;

public class Mp4VideoUtilsTest {

    private Mp4VideoUtils mp4VideoUtils;

    @Test
    public void generateMp4() {
        String ffmpegPath = "D:\\Java\\ffmpeg\\bin\\ffmpeg.exe";//ffmpeg的安装位置
        String videoPath = "D:\\develop\\lucene.avi";
        String mp4Name = "lucene.mp4";
        String mp4Path = "D:\\develop\\mp4\\";
        mp4VideoUtils = new Mp4VideoUtils(ffmpegPath, videoPath, mp4Name, mp4Path);
        String generateMp4 = mp4VideoUtils.generateMp4();
        Assert.assertEquals(generateMp4, "success");
    }
}
