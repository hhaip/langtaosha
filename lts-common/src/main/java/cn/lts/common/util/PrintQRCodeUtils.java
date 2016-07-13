package cn.lts.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

/**
 * 打印二维码工具类
 * author: huanghaiping
 * created on 16-7-13.
 */
public class PrintQRCodeUtils {
    private static final Integer WIDTH = 150;
    private static final Integer HEIGHT = 150;

    private static void print(File file) throws WriterException, IOException, PrintException {
        HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        DocFlavor flavor = DocFlavor.INPUT_STREAM.PNG;
        PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
        DocPrintJob job = defaultService.createPrintJob(); // 创建打印作业
        Object fis = new FileInputStream(file); // 构造待打印的文件流
        DocAttributeSet das = new HashDocAttributeSet();
        Doc doc = new SimpleDoc(fis, flavor, das);
        job.print(doc, pras);
    }

    /**二维码打印
     * @param code: 请求的码或请求的完整地址
     * @param filePath:生成保存图片的地址，如果没有无法生成文件无法打印
     * */
    @SuppressWarnings("deprecation")
    public static Boolean printQRCode(String code, String filePath) {
        try {
            String format = "png";
            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(code, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
            File outputFile = new File(filePath);
            
            if(!outputFile.exists()) {
                outputFile.mkdirs();
            }
            outputFile = new File(filePath);
            if(!outputFile.exists()){
                try {
                    outputFile.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
            print(outputFile);
            return true;
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PrintException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static void main(String[] args) {
        printQRCode("http://jianyong2015.xicp.net/menu/transfer?menu=bindRoom&roomId=1&roomCode=O8nrpRB1430638310748", "C:\\Users\\Administrator\\Desktop\\erweima.png");
        System.out.println("打印完成");
    }
}
