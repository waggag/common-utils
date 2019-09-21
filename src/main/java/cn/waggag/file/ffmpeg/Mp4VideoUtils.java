package cn.waggag.file.ffmpeg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 使用ffmpeg处理视频
 * @author: waggag
 * @time: 2019/9/21
 * @Company http://www.waggag.cn
 */
public class Mp4VideoUtils extends VideoUtils {
    /**
     * ffmpegPath安装路径
     */
    private String ffmpegPath;
    /**
     * 视频路径
     */
    private String videoPath;
    /**
     * mp4的文件名
     */
    private String mp4Name;
    /**
     * mp4文件夹路径
     */
    private String mp4FolderPath;

    public Mp4VideoUtils(String ffmpegPath) {
        super(ffmpegPath);
    }

    public Mp4VideoUtils(String ffmpegPath, String videoPath, String mp4Name, String mp4FolderPath) {
        super(ffmpegPath);
        this.ffmpegPath = ffmpegPath;
        this.videoPath = videoPath;
        this.mp4Name = mp4Name;
        this.mp4FolderPath = mp4FolderPath;
    }

    /**
     * 删除原来已经生成的mp4文件
     * @param mp4Path mp4文件所在路径
     */
    private void clearMp4(String mp4Path) {
        File mp4File = new File(mp4Path);
        if (mp4File.exists() && mp4File.isFile()) {
            mp4File.delete();
        }
    }

    /**
     * 视频编码,将avi文件生成mp4文件
     * ffmpeg.exe -i  lucene.avi -c:v libx264 -s 1280x720 -pix_fmt yuv420p -b:a 63k -b:v 753k -r 18 .\lucene.mp4
     * @return 成功返回success，失败返回控制台日志
     */
    public String generateMp4() {
        this.clearMp4(mp4FolderPath + mp4Name);
        List<String> commend = new ArrayList<>();
        commend.add(ffmpegPath);
        commend.add("-i");
        commend.add(videoPath);
        commend.add("-c:v");
        commend.add("libx264");
        commend.add("-y");//覆盖输出文件
        commend.add("-s");
        commend.add("1280x720");
        commend.add("-pix_fmt");
        commend.add("yuv420p");
        commend.add("-b:a");
        commend.add("63k");
        commend.add("-b:v");
        commend.add("753k");
        commend.add("-r");
        commend.add("18");
        commend.add(mp4FolderPath + mp4Name);
        String outString = null;
        Process process = null;
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            //将标准输入流和错误输入流合并，通过标准输入流程读取信息
            builder.redirectErrorStream(true);
            process = builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取命令执行后的输出参数
        outString = waitFor(process);

        //通过判断视频时间判断是否成功
        Boolean checkVideoTime = this.checkVideoTime(videoPath, mp4FolderPath + mp4Name);
        if (checkVideoTime) {
            return "success";
        } else {
            return outString;
        }
    }
}
