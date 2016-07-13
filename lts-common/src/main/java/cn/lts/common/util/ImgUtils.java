package cn.lts.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;



/**
 * 图片工具类
 * @project uparty-common
 * @author czz
 * @date 2015年5月6日上午10:51:22
 */
public class ImgUtils {
    
    private static Font font = new Font("微软雅黑", Font.BOLD, 84);// 添加字体的属性设置
    private static Graphics2D g2d = null;
    
    /**反转图片
     * @param sourceFile:源文件
     * @param destFile:反转后的文件
     * @param fileType:文件类型
     * @param direcion:反转的方向,0为上下反转 ,1为左右反转
     * */
    public static void reverseImg(String sourceFile,String destFile,String fileType,int direcion){
        try {
            BufferedImage bi = ImageIO.read(new File(sourceFile));
            int width = bi.getWidth();
            int height = bi.getHeight();
            int [][]datas = new int[width][height];
            int [][]datastmp = new int[width][height];
            for(int i = 0;i< height;i++){
                for(int j = 0 ;j<width;j++){
                    datas[j][i]= bi.getRGB(j, i);
                    datastmp[j][i]= bi.getRGB(j, i);
                }
            }
            int [][] xz = reverse(datas,width,height,direcion);
            for(int i = 0;i< height;i++){
                for(int j = 0 ;j<width;j++){
                    bi.setRGB(j, i, xz[j][i]);
                }
            }
            ImageIO.write(bi, fileType, new File(destFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /***
     * 图片旋转数据处理
     * @param direcion 0 为上下反转 1 为左右反转
     * @param datas
     * @param width
     * @param height
     */
    public static int[][] reverse(int[][] datas, int width, int height, int direcion) {
        try {
            int[][] tmps = new int[width][height];
            if (direcion == 0) {
                for (int i = 0, a = height - 1; i < height; i++, a--) {
                    for (int j = 0, b = width - 1; j < width; j++, b--) {
                        tmps[b][a] = datas[j][i];
                    }
                }
            } else if (direcion == 1) {
                for (int i = 0; i < height; i++) {
                    for (int j = 0, b = width - 1; j < width; j++, b--) {
                        tmps[b][i] = datas[j][i];
                    }
                }
            }
            return tmps;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /** 
     * 拼接多张图片 
     *  
     * @param pics 
     * @param type 
     * @param dst_pic 
     * @return 
     */  
    public static boolean merge(String[] pics, String type, String dst_pic) {  
  
        int len = pics.length;  
        if (len < 1) {  
            return false;  
        }  
        File[] src = new File[len];  
        BufferedImage[] images = new BufferedImage[len];  
        int[][] ImageArrays = new int[len][];  
        for (int i = 0; i < len; i++) {  
            try {  
                src[i] = new File(pics[i]);  
                images[i] = ImageIO.read(src[i]);  
            } catch (Exception e) {  
                e.printStackTrace();  
                return false;  
            }  
            int width = images[i].getWidth();  
            int height = images[i].getHeight();  
            ImageArrays[i] = new int[width * height];// 从图片中读取RGB   
            ImageArrays[i] = images[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);  
        }  
  
        int dst_height = 0;  
        int dst_width = images[0].getWidth();  
        for (int i = 0; i < images.length; i++) {  
            dst_width = dst_width > images[i].getWidth() ? dst_width : images[i].getWidth();  
            dst_height += images[i].getHeight();  
        }   
        if (dst_height < 1) {  
            return false;  
        }  
  
        // 生成新图片   
        try {    
            BufferedImage ImageNew = new BufferedImage(dst_width, dst_height, BufferedImage.TYPE_INT_RGB);  
            int height_i = 0;  
            for (int i = 0; i < images.length; i++) {  
                ImageNew.setRGB(0, height_i, dst_width, images[i].getHeight(),ImageArrays[i], 0, dst_width);  
                height_i += images[i].getHeight();  
            }  
            File outFile = new File(dst_pic);  
            ImageIO.write(ImageNew, type, outFile);// 写图片   
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
        return true;  
    } 
    
    /**从一个流得到一个反转后的流
     * @param inputBuffer:输入流
     * @param direcion:反转的方向,0为上下反转 ,1为左右反转
     * @param isnew:是不是新的
     * */
    public static BufferedImage reverseBuffer(BufferedImage inputBuffer,int direcion,boolean isnew){
        int width = inputBuffer.getWidth();
        int height = inputBuffer.getHeight();
        int [][]datas = new int[width][height];
        int [][]datastmp = new int[width][height];
        for(int i = 0;i< height;i++){
            for(int j = 0 ;j<width;j++){
                datas[j][i]= inputBuffer.getRGB(j, i);
                datastmp[j][i]= inputBuffer.getRGB(j, i);
            }
        }
        int [][] xz = reverse(datas,width,height,direcion);
        BufferedImage outbuffer;
        if(isnew){
            outbuffer = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        }else{
            outbuffer = inputBuffer;
        }
        for(int i = 0;i< height;i++){
            for(int j = 0 ;j<width;j++){
                outbuffer.setRGB(j, i, xz[j][i]);
            }
        }
        return outbuffer; 
    }
    
    /** 
     * 将输入流数组,拼接多张图片 
     *  
     * @param pics 
     * @param type 
     * @param dst_pic 
     * @return 
     */  
    public static boolean mergeBuffer(BufferedImage[] bufferArr, String type, String dst_pic) {  
  
        int len = bufferArr.length;  
        if (len < 1) {  
            return false;  
        }  
  
        int[][] ImageArrays = new int[len][];  
        for (int i = 0; i < len; i++) {  
            int width = bufferArr[i].getWidth();  
            int height = bufferArr[i].getHeight();  
            ImageArrays[i] = new int[width * height];// 从图片中读取RGB   
            ImageArrays[i] = bufferArr[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);  
        }  
  
        int dst_height = 0;  
        int dst_width = bufferArr[0].getWidth();  
        for (int i = 0; i < bufferArr.length; i++) {  
            dst_width = dst_width > bufferArr[i].getWidth() ? dst_width : bufferArr[i].getWidth();  
            dst_height += bufferArr[i].getHeight();  
        }   
        if (dst_height < 1) {  
            return false;  
        }  
  
        // 生成新图片   
        try {    
            BufferedImage ImageNew = new BufferedImage(dst_width, dst_height, BufferedImage.TYPE_INT_RGB);  
            int height_i = 0;  
            for (int i = 0; i < bufferArr.length; i++) {  
                ImageNew.setRGB(0, height_i, dst_width, bufferArr[i].getHeight(),ImageArrays[i], 0, dst_width);  
                height_i += bufferArr[i].getHeight();  
            }  
            File outFile = new File(dst_pic);  
            ImageIO.write(ImageNew, type, outFile);// 写图片 
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
        return true;  
    } 
    
    /** 
     * 导入本地图片到缓冲区 
     */  
    public static BufferedImage loadImageLocal(String imgName) {  
        try {  
            return ImageIO.read(new File(imgName));  
        } catch (IOException e) {  
            e.printStackTrace();
        }  
        return null;  
    }
    
    /** 
     * 导入网络图片到缓冲区 
     */  
    public BufferedImage loadImageUrl(String imgName) {  
        try {  
            URL url = new URL(imgName);  
            return ImageIO.read(url);  
        } catch (IOException e) {  
            e.printStackTrace();
        }  
        return null;  
    } 
    
    /** 
     * 修改图片,将文本写入到图片。
     * @return 返回修改后的图片缓冲区（只输出一行文本）
     * @param img: 图片缓冲流
     * @param content:写入的文本内容
     * @param 写入的X位置
     * @param 写入的Y位置
     */  
    public static BufferedImage modifyImage(BufferedImage img, String content, int x, int y) {
        try {    
            g2d = img.createGraphics();  
            g2d.setBackground(Color.WHITE);  
            g2d.setColor(Color.BLACK);//设置字体颜色  
            if (font != null){
                g2d.setFont(font);
            }
            if (content != null) {  
                g2d.drawString(content, x, y);  
            }  
            g2d.dispose();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return img;  
    }
    
    /**
     * 将一个图片流写入另一个图片流
     * */
    public static BufferedImage modifyBuffer(BufferedImage small, BufferedImage big,int x, int y) {  
        try {  
            int w = small.getWidth();  
            int h = small.getHeight();  
            g2d = big.createGraphics();  
            g2d.drawImage(small, x, y, w, h, null);  
            g2d.dispose();  
        } catch (Exception e) {  
            e.printStackTrace();
        }  
        return big;  
    }
    
    /** 
     * 生成新图片到本地 
     * @param 生成的新图片地址
     * @param 图片缓冲流
     * 
     */  
    public static void writeImageLocal(String newImage, BufferedImage img) {  
        if (newImage != null && img != null) {  
            try {  
                File outputfile = new File(newImage);  
                ImageIO.write(img, "png", outputfile);  
            } catch (IOException e) {  
                e.printStackTrace(); 
            }  
        }  
    }
    
    public static void main(String[] args) throws IOException {
//        BufferedImage templet = ImgUtils.loadImageLocal("D:\\test\\img\\room_big.png");
//        BufferedImage code = ImgUtils.loadImageLocal("D:\\test\\img\\code250.png");
//        BufferedImage buffer_big = ImgUtils.modifyBuffer(code, templet,515,235);
//        ImageIO.write(buffer_big, "png", new File("D:\\test\\img\\room_big2.png"));
        
        BufferedImage big = ImgUtils.loadImageLocal("D:\\test\\img\\西安.png");
        BufferedImage code = ImgUtils.loadImageLocal("D:\\test\\img\\50.png");
        BufferedImage buffer_small = ImgUtils.modifyBuffer(code, big,190,190);
        ImageIO.write(buffer_small, "png", new File("D:\\test\\img\\西安2.png"));
    }
}
