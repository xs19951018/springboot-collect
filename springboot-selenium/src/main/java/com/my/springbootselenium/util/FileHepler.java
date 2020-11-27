package com.my.springbootselenium.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

@Slf4j
@Component
public class FileHepler {

    private File file;
    private static String SERVER_PATH = "D:\\fileupload";

    // 静态代码块，只执行一次
    static {
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            SERVER_PATH = "D:\\fileupload";
        } else {
            // unix or linux
            SERVER_PATH = "usr/local/fileupload";
        }
        File server = new File(SERVER_PATH);
        if (!server.isDirectory()) {
            server.mkdirs();
        }
    }


    /**
     * 下载文件
     */
    public void download(String url) {
        File file = new File(url);
        if (!file.exists()) {
            log.info("资源文件不存在");
            return;
        }
        log.info("========== 文件下载开始 ==========");
        String savePath = initSavePath(url);
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            bos = new BufferedOutputStream(new FileOutputStream(new File(savePath)));
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = bis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (bis != null) bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("========== 文件下载结束 ==========");
    }

    public void downloadFronInet(String url) {
        log.info("========== 文件下载开始 ==========");
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            URL serverUrl = new URL(url);
            URLConnection conn = serverUrl.openConnection();
            // 设置超时
            conn.setConnectTimeout(3*1000);
            conn.setRequestProperty("Accept-Encoding", "identity");
            int length = conn.getContentLength();
            // 初始化资源存储路径
            String savePath = initSavePath(url);
            is = conn.getInputStream();
            fos = new FileOutputStream(savePath);
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = is.read(b)) != -1) {
                fos.write(b, 0, len);
            }
            log.info("保存路径" + savePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("========== 文件下载结束 ==========");
    }

    /**
     * 创建存储路径，并返回
     * @return
     */
    private String initSavePath(String url) {
        String name = url.substring(url.replaceAll("/", "\\\\").lastIndexOf("\\") + 1);
        String suffix = name.substring(name.indexOf(".") + 1).toUpperCase();
        String savePath = SERVER_PATH + File.separator + suffix;
        File saveDir = new File(savePath);
        if (!saveDir.isDirectory()) {
            saveDir.mkdirs();
        }
        return savePath + File.separator + name;
    }
}
