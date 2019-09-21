package cn.waggag.file.ffmpeg;

import cn.waggag.file.UpLoadUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 此文件用于视频文件处理，步骤如下：
 * 1、生成mp4
 * 2、生成m3u8
 */
public class HlsVideoUtils extends VideoUtils {
    /**
     * ffmpegPath安装路径
     */
    private String ffmpegPath;
    /**
     * 视频路径
     */
    private String videoPath;
    /**
     * m3u8的文件名
     */
    private String m3u8Name;
    /**
     * m3u8文件夹路径
     */
    private String m3u8FolderPath;

    public HlsVideoUtils(String ffmpegPath, String videoPath, String m3u8Name, String m3u8FolderPath) {
        super(ffmpegPath);
        this.ffmpegPath = ffmpegPath;
        this.videoPath = videoPath;
        this.m3u8Name = m3u8Name;
        String dir = UpLoadUtils.getDir(m3u8Name);
        //根据文件名生成对应目录
        this.m3u8FolderPath = m3u8FolderPath+dir;
    }

    /**
     * 删除原来已经生成的m3u8及ts文件
     * @param m3u8Path
     */
    private void clearM3u8(String m3u8Path) {
        //删除原来已经生成的m3u8及ts文件
        File m3u8Dir = new File(m3u8Path);
        if (m3u8Dir.exists()) {
            String[] children = m3u8Dir.list();
            //删除目录中的文件
            for (int i = 0; i < children.length; i++) {
                File file = new File(m3u8Path, children[i]);
                if(file.isFile()){
                    file.delete();
                }
            }
        }else{
            m3u8Dir.mkdirs();
        }
    }

    /**
     * 视频编码,将mp4文件转为m3u8文件
     * ffmpeg -i  lucene.mp4   -hls_time 10 -hls_list_size 0   -hls_segment_filename ./hls/lucene_%05d.ts ./hls/lucene.m3u8
     * @return 成功则返回success，失败返回控制台日志
     */
    public String generateM3u8() {
        //清理m3u8文件目录
        this.clearM3u8(m3u8FolderPath);
        List<String> commends = new ArrayList<String>();
        commends.add(ffmpegPath);
        commends.add("-i");
        commends.add(videoPath);
        commends.add("-hls_time");
        commends.add("10");
        commends.add("-hls_list_size");
        commends.add("0");
        commends.add("-hls_segment_filename");
        commends.add(m3u8FolderPath + m3u8Name.substring(0, m3u8Name.lastIndexOf(".")) + "_%05d.ts");
        commends.add(m3u8FolderPath + m3u8Name);
        Process process = null;
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commends);
            //将标准输入流和错误输入流合并，通过标准输入流程读取信息
            builder.redirectErrorStream(true);
            process = builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取输出参数
        String outString = waitFor(process);

        //通过查看m3u8列表判断是否成功
        List<String> tsList = getsTsList();
        if (tsList == null) {
            return outString;
        }
        return "success";
    }

    /**
     * 检查视频处理是否完成
     * @return ts列表
     */
    public List<String> getsTsList() {
        List<String> fileList = new ArrayList<>();
        List<String> tsList = new ArrayList<>();
        String m3u8FilePath = m3u8FolderPath + m3u8Name;
        BufferedReader bufferedReader = null;
        String str;
        String bottomLine = "";
        try {
            bufferedReader = new BufferedReader(new FileReader(m3u8FilePath));
            while ((str = bufferedReader.readLine()) != null) {
                bottomLine = str;
                if (bottomLine.endsWith(".ts")) {
                    tsList.add(bottomLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (bottomLine.contains("#EXT-X-ENDLIST")) {
            fileList.addAll(tsList);
            return fileList;
        }
        return null;
    }


    public static void main(String[] args) throws IOException {

    }
}
