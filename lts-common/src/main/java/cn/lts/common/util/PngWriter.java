package cn.lts.common.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 图片定位写入图片
 * @project uparty-common
 * @author czz
 * @date 2015年5月7日上午10:04:23
 */
public class PngWriter {

    public static void main(String[] args) throws IOException  {
        BufferedImage templet = ImgUtils.loadImageLocal("D:\\test\\img\\zp-bbyc.png");
        BufferedImage buffer_text = ImgUtils.modifyImage(templet,"A301 房间",1800,1125);
        BufferedImage code = ImgUtils.loadImageLocal("D:\\test\\img\\code615.png");
        
        BufferedImage buffer_suffer = ImgUtils.modifyBuffer(code, buffer_text,1710,390);
        BufferedImage buffer_prifix = ImgUtils.reverseBuffer(buffer_suffer, 0,true);
        
        BufferedImage[] bufferArr = new BufferedImage[2];
        bufferArr[0] = buffer_prifix;
        bufferArr[1] = buffer_suffer; 
        ImgUtils.mergeBuffer(bufferArr, "PNG", "D:\\test\\img\\zp-bbyc999.png");
    }
}
