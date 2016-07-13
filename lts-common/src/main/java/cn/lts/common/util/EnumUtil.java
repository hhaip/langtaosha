package cn.lts.common.util;

import java.util.ArrayList;
import java.util.List;

import cn.lts.common.domain.DescriptionId;

/**
 * @author huanghaiping
 * created on 16-7-13.
 */
public class EnumUtil {

    /**
     * 根据一个索引得到某个枚举类的枚举值
     */
    public static <I extends DescriptionId> I getEnum(Class<I> type, int index) {
        I[] types = type.getEnumConstants();
        for (I t : types) {
            if (t.getIndex() == index) {
                return t;
            }
        }
        throw new AssertionError("不能够映射:" + index + "到枚举" + type.getSimpleName());
    }

    /**
     * 根据一个枚举描述得到某个枚举类的枚举值
     */
    public static <I extends DescriptionId> I getEnum(Class<I> type, String description) {
        I[] types = type.getEnumConstants();
        for (I t : types) {
            if (t.getDescription().equals(description)) {
                return t;
            }
        }
        throw new AssertionError("不能够映射:" + description + "到枚举" + type.getSimpleName());
    }

    /**
     * 得到某枚举类中所有的值的描述
     */
    public static <I extends DescriptionId> List<String> getDescriptions(Class<I> type) {
        I[] types = type.getEnumConstants();
        List<String> result = new ArrayList<String>();
        for (I t : types) {
            result.add(t.getDescription());
        }
        return result;
    }

}
