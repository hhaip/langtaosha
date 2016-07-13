package cn.lts.common.domain.enumeration;

import cn.lts.common.domain.DescriptionId;
import cn.lts.common.util.EnumUtil;

public enum Gender implements DescriptionId {
	UNKNOW(0, "未知"),
	MALE(1, "男"),
	FEMALE(2, "女");
	
	private int index;
    private String description;
    
    private Gender(int index, String description) {
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
	
	public static Gender findByIndex(int index){
		return EnumUtil.getEnum(Gender.class, index);
	}
}