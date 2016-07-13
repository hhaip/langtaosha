package cn.lts.common.domain.enumeration;

import cn.lts.common.domain.DescriptionId;
import cn.lts.common.util.EnumUtil;

/** 
 * author: huanghaiping
 * created on 16-7-13.
 */
public enum QrCodeType implements DescriptionId {
	
	IMAGE_QRCODE(0,"图片二维码"),
	SEQUENCE_QRCODE(1,"二维码内置地址");

	private int index;
	
	private String description;
	
	private QrCodeType(int index, String description) {
		this.index = index;
		this.description = description;
	}
	
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static QrCodeType findByIndex(int index){
        return EnumUtil.getEnum(QrCodeType.class, index);
    }
}
