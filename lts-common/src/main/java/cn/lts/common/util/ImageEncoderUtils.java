package cn.lts.common.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**图片格式转换
 * @project uparty-common
 * @author czz
 * **/
public class ImageEncoderUtils {

	private static ImageEncoderUtils instance;

    private ImageEncoderUtils() {
        instance = this;
    }

    public static ImageEncoderUtils getInstance() {
        if (instance == null) {
            instance = new ImageEncoderUtils();
        }
        return instance;
    }

    /**
     * 缩小并转换格式
     * 
     * @param srcPath源路径
     * @param destPath目标路径
     * @param height目标高
     * @param width
     *            目标宽
     * @param formate
     *            文件格式
     * @return
     */
    public static boolean narrowAndFormateTransfer(String srcPath, String destPath, int height, int width, String formate) {
        boolean flag = false;
        try {
            File file = new File(srcPath);
            File destFile = new File(destPath);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdir();
            }
            BufferedImage src = ImageIO.read(file); // 读入文件
            Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            flag = ImageIO.write(tag, formate, new FileOutputStream(destFile));// 输出到文件流
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static void main(String[] args) {
        try {
//            ut.narrowAndFormateTransfer("D:\\test\\img\\西安.jpg", "D:\\test\\img\\西安.png", 430, 430, "png");
//            boolean flag = ut.narrowAndFormateTransfer("D:\\test\\img\\西安2.png", "D:\\test\\img\\西安big.jpg", 2000, 2000, "jpg");
            boolean flag = ImageEncoderUtils.narrowAndFormateTransfer("D:\\test\\img\\6397.jpg", "D:\\test\\img\\6397-2.jpg", 598*2, 409*2, "jpg");
            System.out.println(flag);
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
}
