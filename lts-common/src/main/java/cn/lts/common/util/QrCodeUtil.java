package cn.lts.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

/**
 * 利用zxing生成/解析二维码
 * @author zhoujianyong
 * 2015年4月22日-下午9:46:23
 */
public class QrCodeUtil {
	/**
	 * 生成二维码图片-写入file
	 * @param width 图片宽度
	 * @param height 图片高度
	 * @param content 二维码内容。文本支持key/value形式的字符串
	 * @param fileAbsolute 文件路径（包括文件名）
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void encodeToFile(int width, int height, String content, String fileAbsolute) throws WriterException, IOException {
		File file = new File(fileAbsolute);
		if(!file.getParentFile().exists()) {
		    file.getParentFile().mkdirs();
        }
		file = new File(fileAbsolute);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		
		if(!file.isDirectory()){
			String filePath = fileAbsolute.substring(0, fileAbsolute.lastIndexOf(isWin() ? "\\" : "/"));
			String fileName = file.getName();
			String fileType = fileAbsolute.substring(fileAbsolute.lastIndexOf(".")+1);
			Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			hints.put(EncodeHintType.MARGIN, 0);
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
			Path path = FileSystems.getDefault().getPath(filePath, fileName);
			MatrixToImageWriter.writeToPath(bitMatrix, fileType, path);// 输出图像
		}
	}
	
	/**
	 * 生成二维码图片-写入steam流
	 * @param width
	 * @param height
	 * @param content
	 * @param stream
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void encodeToStream(int width, int height, String content, OutputStream stream) throws WriterException, IOException {
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
		MatrixToImageWriter.writeToStream(bitMatrix, "jpg", stream);
	}
	
    public static BufferedImage endcodeToInputStream(int width, int height, String content) throws Exception {  
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();   
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");  
        hints.put(EncodeHintType.MARGIN, 0);  
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE, width, height, hints);  
        int wid = bitMatrix.getWidth();  
        int hei = bitMatrix.getHeight();  
        BufferedImage image = new BufferedImage(wid, hei,BufferedImage.TYPE_INT_RGB);  
        for (int x = 0; x < wid; x++) {  
            for (int y = 0; y < hei; y++) {  
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);  
            }  
        }  
        return image;  
    }

	/**
	 * 解析图片二维码
	 * @param fileAbsolute 文件路径（包括文件名）
	 */
	public static void decodeFromFile(String fileAbsolute) {
		BufferedImage image;
		try {
			image = ImageIO.read(new File(fileAbsolute));
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
			Result result =  new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
			System.out.println(result.getText());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean isWin(){
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		return (os.startsWith("win") || os.startsWith("Win"));
	}
	
	public static void main(String[] args) throws WriterException, IOException {
	    encodeToFile(100, 100, "http://test.uparty.cn/transfer?34^cm", "D:\\test\\img\\code100.png");
//	    decodeFromFile("D:\\test\\img\\code100.png");
	    try {
            test();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
	private static void test() throws Exception{
	    String host = "http://test.uparty.cn";
        String rootPath = "D:/test/img/out";
        String bigImg = "D:/test/img/in/room_big.png";//大图片目录
        String smallImg = "D:/test/img/in/room_small.png";//小图片目录
        String contURL = host + "/transfer?34^cm";
        
//        String contURL = "http://test.uparty.cn/menu/transfer?menu=bindRoom&roomId=1&roomCode=mpfmw261444634551646";
        
//        /**大图片中二维码的大小*/
        final int big_code_size = 250;
        final int big_code_x = 515;
        final int big_code_y = 235;
        /**小图片中二维码的大小*/
        final int small_code_size = 120;
        final int small_code_x = 5;
        final int small_code_y = 5;
	    
//	    //导入大图片
        BufferedImage big_img = ImgUtils.loadImageLocal(bigImg);
        //创建大二维码流
        BufferedImage big_code = endcodeToInputStream(big_code_size, big_code_size, contURL);
        //二维码定位写入
        BufferedImage big_buffer = ImgUtils.modifyBuffer(big_code, big_img, big_code_x, big_code_y);
        String bigUrl = rootPath + "/1/1_big.png";
        ImageIO.write(big_buffer, "png", new File(bigUrl));
        
        //导入小图片
        BufferedImage small_img = ImgUtils.loadImageLocal(smallImg);
        //创建小二维码流
        BufferedImage small_code = QrCodeUtil.endcodeToInputStream(small_code_size, small_code_size, contURL);
        //二维码定位写入
        BufferedImage small_buffer = ImgUtils.modifyBuffer(small_code, small_img, small_code_x, small_code_y);
        String smallUrl = rootPath + "/1/1_small.png";
        ImageIO.write(small_buffer, "png", new File(smallUrl));
	}
}