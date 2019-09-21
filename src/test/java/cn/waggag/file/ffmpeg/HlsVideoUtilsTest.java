package cn.waggag.file.ffmpeg;

import org.junit.Test;

public class HlsVideoUtilsTest {

    private HlsVideoUtils hlsVideoUtils;

    @Test
    public void generateM3u8() {
        String ffmpegPath = "D:\\Java\\ffmpeg\\bin\\ffmpeg.exe";//ffmpeg的安装位置
        String videoPath = "D:\\develop\\mp4\\demo.mp4";
        String m3u8Name = "demo.m3u8";
        String m3u8Path = "D:\\develop\\m3u8";
        hlsVideoUtils = new HlsVideoUtils(ffmpegPath, videoPath, m3u8Name, m3u8Path);
        String generateM3u8 = hlsVideoUtils.generateM3u8();
        System.out.println(generateM3u8);
        System.out.println(hlsVideoUtils.getsTsList());
    }
}
