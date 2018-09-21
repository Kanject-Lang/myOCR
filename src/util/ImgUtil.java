package util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * @description: TODO
 * @author: Kanject
 */
public class ImgUtil {



    /**
     * 图片裁切
     * @param x1 选择区域左上角的x坐标
     * @param y1 选择区域左上角的y坐标
     * @param width 选择区域的宽度
     * @param height 选择区域的高度
     * @param sourcePath 源图片路径
     * @param destpath 裁切后图片的保存路径
     */
    public static void cut(int x1, int y1, int width, int height,
                           String sourcePath, String destpath) {

        FileInputStream is = null;
        ImageInputStream iis = null;
        try {
            is = new FileInputStream(sourcePath);
            String fileSuffix = sourcePath.substring(sourcePath
                    .lastIndexOf(".") + 1);
            Iterator<ImageReader> it = ImageIO
                    .getImageReadersByFormatName(fileSuffix);
            ImageReader reader = it.next();
            iis = ImageIO.createImageInputStream(is);
            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();
            Rectangle rect = new Rectangle(x1, y1, width, height);
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            ImageIO.write(bi, fileSuffix, new File(destpath));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                is = null;
            }
            if (iis != null) {
                try {
                    iis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                iis = null;
            }
        }

    }

    /**
     *
     * @Title: 构造图片
     * @Description: 生成水印并返回java.awt.image.BufferedImage
     * @param srcFile
     *            源文件(图片)
     * @return BufferedImage
     * @throws IOException
     */
    public static BufferedImage compose(File srcFile) throws IOException {
        // 获取底图
        BufferedImage bi = new BufferedImage(1500, 500, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)bi.getGraphics();
        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, 1500, 500);

        // 获取层图
        BufferedImage waterImg = ImageIO.read(srcFile);
        // 创建Graphics2D对象，用在底图对象上绘图
        Graphics2D g2d = bi.createGraphics();
        int waterImgWidth = waterImg.getWidth();// 获取层图的宽度
        int waterImgHeight = waterImg.getHeight();// 获取层图的高度
        // 在图形和图像中实现混合和透明效果
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
        // 绘制
        g2d.drawImage(waterImg, 0, 0, waterImgWidth, waterImgHeight, null);
        g2d.dispose();// 释放图形上下文使用的系统资源
        return bi;
    }

    /**
     * 输出水印图片
     * @param buffImg
     *            图像加水印之后的BufferedImage对象
     * @param savePath
     *            图像加水印之后的保存路径
     */
    public static void generate(BufferedImage buffImg, String savePath) {
        int temp = savePath.lastIndexOf(".") + 1;
        try {
            ImageIO.write(buffImg, savePath.substring(temp), new File(savePath));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
