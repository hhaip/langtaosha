package cn.lts.mobile.util;

import net.sf.json.JSONObject;


/**
 * 响应页面请求时返回的json消息对象
 * @author zhoujianyong
 */
public class JSONMessage {

    private boolean ok = true; //true-成功(success), false-失败(fail)
    private int code = 0;
    private String msg = ""; //消息文字
    private JSONObject result = JSONHelper.buildJSONObject("{}"); //json格式的返回结果,status为true时有效
    
    public static JSONMessage newMessageOnFail() {
        JSONMessage jsonMessage = new JSONMessage();
        jsonMessage.setOk(false);
        return jsonMessage;
    }

    public static JSONMessage newMessageOnSuccess() {
        return new JSONMessage();
    }
    
    /**
     * 转换为json字符串
     * @return
     */
    public String toJSONString() {
        return this.toJSONObject().toString();
    }
    
    /**
     * 转换为json对象
     * @return
     */
    public JSONObject toJSONObject() {
        JSONObject jsonMessage = JSONHelper.buildJSONObject("{status:'fail',code:'0',msg:'',result:{}}");
        jsonMessage.element("status", this.ok?"success":"fail");
        jsonMessage.element("code", this.code);
        jsonMessage.element("msg", this.msg);
        jsonMessage.element("result", this.result);
        return jsonMessage;
    }
    
    /**
     * 对异常情况时消息格式的处理
     * @param e
     */
    public void onException(int errorCode, Exception e) {
        this.setOk(false);
        this.setCode(errorCode);
        this.setMsg(e.getMessage());
        this.setResult(JSONHelper.buildJSONObject("{}"));
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isOk() {
        return ok;
    }
    public void setOk(boolean ok) {
        this.ok = ok;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public JSONObject getResult() {
        return result;
    }
    public void setResult(JSONObject result) {
        this.result = result;
    }
}