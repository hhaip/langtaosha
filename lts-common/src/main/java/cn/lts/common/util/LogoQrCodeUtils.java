package cn.lts.common.util;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

/**
 * 生成带logo的二维码
 * @project uparty-common
 * @author czz
 * @date 2015年5月7日下午3:48:30
 */
public class LogoQrCodeUtils {
        private static final String CHARSET = "utf-8";  
        private static final String FORMAT_NAME = "PNG";    

        public static BufferedImage createImage(String content, String imgPath,int qrcode_size,int log_size,boolean needCompress) throws Exception {  
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();  
            hints.put(EncodeHintType.CHARACTER_SET, CHARSET);  
            hints.put(EncodeHintType.MARGIN, 0);  
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE, qrcode_size, qrcode_size, hints);
            
            int[] rec = bitMatrix.getEnclosingRectangle();
            int resWidth = rec[2] + 1;  
            int resHeight = rec[3] + 1;  
            BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);  
            resMatrix.clear();  
            for (int i = 0; i < resWidth; i++) {  
                for (int j = 0; j < resHeight; j++) {  
                    if (bitMatrix.get(i + rec[0], j + rec[1])) { 
                         resMatrix.set(i, j); 
                    } 
                }  
            } 
            
            int width = resMatrix.getWidth();  
            int height = resMatrix.getHeight();  
            BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);  
            for (int x = 0; x < width; x++) {  
                for (int y = 0; y < height; y++) {  
                    image.setRGB(x, y, resMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);  
                }  
            }  
            if (imgPath == null || "".equals(imgPath)) {  
                return image;  
            } 
            // 插入图片  
            insertImage(image, imgPath,width,log_size,needCompress);  
            return image;  
        }  

        /** 
         * 插入LOGO 
         *  
         * @param source 
         *            二维码图片 
         * @param imgPath 
         *            LOGO图片地址 
         * @param needCompress 
         *            是否压缩 
         * @throws Exception 
         */  
        private static void insertImage(BufferedImage source, String imgPath,int QRCODE_SIZE,int log_size,boolean needCompress) throws Exception {  
            File file = new File(imgPath);  
            if (!file.exists()) {  
                System.err.println(""+imgPath+"   该文件不存在！");  
                return;  
            }  
            Image src = ImageIO.read(new File(imgPath));  
            int width = src.getWidth(null);  
            int height = src.getHeight(null);  
            if (needCompress) { // 压缩LOGO  
                if (width > log_size) {  
                    width = log_size;  
                }  
                if (height > log_size) {  
                    height = log_size;  
                }  
                Image image = src.getScaledInstance(width, height,Image.SCALE_SMOOTH);  
                BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);  
                Graphics g = tag.getGraphics();  
                g.drawImage(image, 0, 0, null); // 绘制缩小后的图  
                g.dispose();  
                src = image;  
            }
            // 插入LOGO  
            Graphics2D graph = source.createGraphics();  
            int x = (QRCODE_SIZE - width) / 2;  
            int y = (QRCODE_SIZE - height) / 2;  
            graph.drawImage(src, x, y, width, height, null);  
            Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);  
            graph.setStroke(new BasicStroke(3f));  
            graph.draw(shape);  
            graph.dispose();  
        }  

        /** 
         * 生成二维码(内嵌LOGO) 
         * @param content 内容地址 
         * @param imgPath  LOGO地址
         * @param qrcode_size 生成的二维码的大小
         * @param log_size 二维中间log图片的大小 
         * @param destPath  存放目标地址 
         * @param needCompress  是否压缩LOGO 
         * @throws Exception 
         */  
        public static void encode(String content, String imgPath,int qrcode_size,int log_size,String destUrl,boolean needCompress) throws Exception {  
            BufferedImage image = createImage(content, imgPath,qrcode_size,log_size,needCompress);
            
            File file = new File(destUrl);
            if(!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file = new File(destUrl);
            if(!file.exists()){
                try {
                    file.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ImageIO.write(image, FORMAT_NAME, new File(destUrl));  
        }  

        /** 
         * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常) 
         * @param destPath 存放目录 
         */  
        public static void mkdirs(String destPath) {  
            File file =new File(destPath);        
            if (!file.exists() && !file.isDirectory()) {  
                file.mkdirs();  
            }
        }  

        public static void main(String[] args) throws Exception {  
            encode("http://jianyong2015.xicp.net/menu/transfer?menu=bindRoom&roomId=1&roomCode=mTOrg8A1430982799352", "D:\\test\\img\\pub_logo.png",120,15, "D:\\test\\img\\code120.png", true); 
            //encode("http://test.uparty.cn/menu/transfer?menu=bindRoom&roomId=1&roomCode=mTOrg8A1430982799352", "D:\\test\\img\\15.png",120,15, "D:\\test\\img\\code100.png", true);
            try {
                test();
            } catch (Exception e) {
                // TODO: handle exception
            }
        } 
        
        private static void test() throws Exception{
            /**大图片中二维码的大小*/
            final int big_code_size = 260;
            /**大图片中logo的大小*/
            final int big_logo_size = 30;
            /**二维码在大图片中的坐标*/
            final int big_code_x = 522;
            final int big_code_y = 245;
            /**小图片中二维码的大小*/
            final int small_code_size = 130;
            /**小图片中logo的大小*/
            final int small_logo_size = 15;
            /**二维码在小图片中的坐标*/
            final int small_code_x = 15;
            final int small_code_y = 15;
            /**房间二维码内容详情地址*/
            String ROOM_CODE_CONTENT_URL = "/menu/transfer?menu=bindRoom";
            
            String host = "http://test.uparty.cn";
            String rootPath = "D:/test/img/out";
            String logoUrl = "D:/test/img/pub_logo.png";//logo图片目录
            String bigImg = "D:/test/img/in/room_big.png";//大图片目录
            String smallImg = "D:/test/img/in/room_small.png";//小图片目录
            String contURL = host + ROOM_CODE_CONTENT_URL + "&roomId=1&roomCode=1234567";
            String desc = "/1";
            //导入大图片
            BufferedImage big_img = ImgUtils.loadImageLocal(bigImg);
            //创建大二维码流
            BufferedImage big_code = createImage(contURL, logoUrl, big_code_size, big_logo_size, true);
            //二维码定位写入
            BufferedImage big_buffer = ImgUtils.modifyBuffer(big_code, big_img, big_code_x, big_code_y);
            String bigUrl = rootPath + desc + "/1_big.png";
            ImageIO.write(big_buffer, "png", new File(bigUrl));

            //导入小图片
            BufferedImage small_img = ImgUtils.loadImageLocal(smallImg);
            //创建小二维码流
            BufferedImage small_code = createImage(contURL, logoUrl, small_code_size, small_logo_size, true);
            //二维码定位写入
            BufferedImage small_buffer = ImgUtils.modifyBuffer(small_code, small_img, small_code_x, small_code_y);
            String smallUrl = rootPath + desc + "/1_small.png";
            ImageIO.write(small_buffer, "png", new File(smallUrl));
        }
}  