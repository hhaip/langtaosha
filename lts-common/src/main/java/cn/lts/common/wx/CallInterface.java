package cn.lts.common.wx;

import com.alibaba.fastjson.JSONObject;

/**回调接口 
 * @author zhoujy 
 * @date 创建时间：2015年7月24日 下午3:29:54 
 * @see  
 */
public interface CallInterface {
	public void doCall(JSONObject jsonObj);
}
