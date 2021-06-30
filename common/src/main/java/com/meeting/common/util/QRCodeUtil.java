package com.meeting.common.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.meeting.common.exception.DescribeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.Hashtable;

/**
 * @author: dameizi
 * @description: 二维码图片工具
 * @dateTime 2019-04-11 16:23
 * @className QRCodeUtil
 */
public class QRCodeUtil {

    private static Logger logger = LogManager.getLogger(QRCodeUtil.class);

    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "JPEG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int WIDTH = 60;
    // LOGO高度
    private static final int HEIGHT = 60;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 16:24
     * @description: 生成二维码
     * @param: [content]
     * @return: java.awt.image.BufferedImage
     */
    private static BufferedImage createImage(String content) throws Exception {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }
        return image;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 16:24
     * @description: 生成base64图片返回前台
     * @param: [content]
     * @return: java.lang.String
     */
    public static String encodeBase64(String content) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content);
        // bufferImage->base64
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, FORMAT_NAME, outputStream);
        BASE64Encoder encoder = new BASE64Encoder();
        String base64Img = encoder.encode(outputStream.toByteArray());

        return base64Img;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 16:24
     * @description: 远程解码阿里云图片内容
     * @param: [aliyunUrl]
     * @return: java.lang.String
     */
    public static String decode(String aliyunUrl) {
        try {
            //远程阿里云URL
            URL url = new URL(aliyunUrl);
            BufferedImage image = ImageIO.read(url);
            if (image == null) {
                throw new DescribeException("Could not decode image", 0);
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result;
            Hashtable hints = new Hashtable();
            hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
            //解码设置编码方式为：utf-8，
            result = new MultiFormatReader().decode(bitmap, hints);
            String resultStr = result.getText();
            //返回图片解码后的数据
            return resultStr;
        } catch (Exception e) {
            logger.error("获取阿里云图片解码错误" + e, -1);
        }
        return null;
    }

}
