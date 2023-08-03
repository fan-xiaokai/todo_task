package com.todo.task.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author chenhu
 * @date 2019/5/9
 */
public class ImgUtil {
    /**
     * 二进制流转Base64字符串
     *
     * @param data 二进制流
     * @return data
     * @throws IOException 异常
     */
    public static String getImageString(byte[] data) throws IOException {
        BASE64Encoder encoder = new BASE64Encoder();
        return data != null ? encoder.encode(data) : "";
    }


    /**
     * Base64字符串转 二进制流
     *
     * @param base64String Base64
     * @return base64String
     * @throws IOException 异常
     */
    public static byte[] getStringImage(String base64String) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return base64String != null ? decoder.decodeBuffer(base64String) : null;
    }

    /**
     * word和txt文件转换成图片
     *
     * @param inputStream
     * @return List<BufferedImage>
     * @throws IOException 异常
     */
    public static List<BufferedImage> wordToImg(InputStream inputStream) {
        long old = System.currentTimeMillis();
//            Document doc = new Document(inputStream);
        return null;
    }
}
