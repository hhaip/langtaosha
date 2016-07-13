package cn.lts.common.domain;


/**
 * 数据状态，
 * @author huanghaiping
 * created on 16-7-13.
 */
public enum DataStatus implements DescriptionId {
    //默认值
    DEFAULT(0, "default"),
    
    //已修订，禁止自动更新
    FiXED(1, "已修订"),
    
    //逻辑删除
    DELETED(2, "已删除"),

    PAUSE(3, "停用"),

    DISABLE(4, "失效"),

    //转移到别处
    TRANSFER(5, "已转移"),

    VIRTUAL(6, "虚拟数据"),

    TEMP(7, "临时数据");


    private int index;
    private String description;
    
    private DataStatus(int index, String description) {
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
    
    public static DataStatus findByIndex(int index){
        DataStatus[] types = DataStatus.class.getEnumConstants();
        for (DataStatus t : types) {
            if (t.getIndex() == index) {
                return t;
            }
        }
        throw new AssertionError("不能够映射:" + index + "到枚举" + DataStatus.class.getSimpleName());
    }
}
