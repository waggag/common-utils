package cn.waggag.file.ffmpeg;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 此文件作为视频文件处理父类，提供：
 *  1、查看视频时长
 *  2、校验两个视频的时长是否相等
 * @author: waggag
 * @time: 2019/9/21
 * @Company http://www.waggag.cn
 */
public class VideoUtils {
    //ffmpeg的安装位置
    private static String ffmpegPath = "D:\\Java\\ffmpeg\\bin\\ffmpeg.exe";

    public VideoUtils(String ffmpegPath) {
        this.ffmpegPath = ffmpegPath;
    }

    /**
     * 检查视频时间是否一致
     * @param source 源视频
     * @param target 目标视频
     * @return 视频时间是否一致
     */
    public Boolean checkVideoTime(String source, String target) {
        String sourceTime = getVideoTime(source);
        //取出时分秒
        sourceTime = sourceTime.substring(0, sourceTime.lastIndexOf("."));
        String targetTime = getVideoTime(target);
        //取出时分秒
        targetTime = targetTime.substring(0, targetTime.lastIndexOf("."));
        if (sourceTime == null || targetTime == null) {
            return false;
        }
        if (sourceTime.equals(targetTime)) {
            return true;
        }
        return false;
    }

    /**
     * 执行ffmpeg -i  lucene.mp4截取字符串获取视频时间(时：分：秒：毫秒)
     * @param videoPath 视频的路径
     * @return 返回时间字符串
     */
    public String getVideoTime(String videoPath) {
        List<String> commends = new ArrayList<>();
        commends.add(ffmpegPath);
        commends.add("-i");
        commends.add(videoPath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commends);
            //将标准输入流和错误输入流合并，通过标准输入流程读取信息
            builder.redirectErrorStream(true);
            Process process = builder.start();
            String outString = this.waitFor(process);
            int start = outString.trim().indexOf("Duration: ");
            if (start >= 0) {
                int end = outString.trim().indexOf(", start:");
                if (end >= 0) {
                    String time = outString.substring(start + 10, end);
                    if (time != null && !time.equals("")) {
                        return time.trim();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 获取执行命令输出的正确和错误流的信息
     * @param process 执行进程
     * @return  执行进程输出的字符串
     */
    public String waitFor(Process process) {
        InputStream inputStream = null;
        InputStream error = null;
        int exitValue = -1;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            inputStream = process.getInputStream();
            error = process.getErrorStream();
            boolean finished = false;
            //每次休眠1秒，最长执行时间10分种
            int maxRetry = 600;
            int retry = 0;
            while (!finished) {
                if (retry > maxRetry) {
                    return "error";
                }
                try {
                    while (inputStream.available() > 0) {
                        Character character = new Character((char) inputStream.read());
                        stringBuffer.append(character);
                    }
                    while (error.available() > 0) {
                        Character character = new Character((char) error.read());
                        stringBuffer.append(character);
                    }
                    //进程未结束时调用exitValue将抛出异常
                    exitValue = process.exitValue();
                    finished = true;
                } catch (IllegalThreadStateException e) {
                    Thread.currentThread().sleep(1000);//休眠1秒
                    retry++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
            if(error != null){
                try {
                    error.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuffer.toString();
    }
}
