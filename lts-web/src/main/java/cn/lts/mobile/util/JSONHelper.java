package cn.lts.mobile.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONFunction;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONTokener;
import net.sf.json.util.JsonEventListener;

import java.util.Map;


public class JSONHelper {

    public static JsonConfig JSON_CONFIG = new JsonConfig();
    static {
        JSON_CONFIG.addJsonEventListener(new DefaultJsonEventListenerImpl());
    }
    
    public static JSONNull getJSONNull() {
        return JSONNull.getInstance();
    }
    
    public static JSONTokener buildJSONTokener(String s) {
        return new JSONTokener(s);
    }
    

   /**
    * Creates a JSONArray.<br>
    * Inspects the object type to call the correct JSONArray factory method.
    * Accepts JSON formatted strings, arrays, Collections and Enums.
    *
    * @param object
    * @throws JSONException if the object can not be converted to a proper
    *         JSONArray.
    */
    public static JSONArray buildJSONArray(Object object) {
        return JSONArray.fromObject(object);
    }
    
   /**
    * Creates a JSONArray.<br>
    * Inspects the object type to call the correct JSONArray factory method.
    * Accepts JSON formatted strings, arrays, Collections and Enums.
    *
    * @param object
    * @throws JSONException if the object can not be converted to a proper
    *         JSONArray.
    */
    public static JSONArray buildJSONArray(Object object, JsonConfig jsonConfig) {
        return JSONArray.fromObject(object, jsonConfig);
    }
    
    /**
     * buildJSONFunction
     * @param functionStr, e.g. function (message){alert(message);}"
     * @return
     */
    public static JSONFunction buildJSONFunction(String functionStr) {
        return JSONFunction.parse(functionStr);
    }

   /**
    * Creates a JSONObject.<br>
    * Inspects the object type to call the correct JSONObject factory method.
    * Accepts JSON formatted strings, Maps, DynaBeans and JavaBeans.
    *
    * @param object e.g. "" or null
    * @throws JSONException if the object can not be converted to a proper
    *         JSONObject.
    */
    public static JSONObject buildJSONObject(Object object) {
        if (object == null || object.equals("")) {
            return JSONObject.fromObject("{}");
        }
        return JSONObject.fromObject(object);
    }

   /**
    * Creates a JSONObject.<br>
    * Inspects the object type to call the correct JSONObject factory method.
    * Accepts JSON formatted strings, Maps, DynaBeans and JavaBeans.
    *
    * @param object e.g. "" or null
    * @throws JSONException if the object can not be converted to a proper
    *         JSONObject.
    */
    public static JSONObject buildJSONObject(Object object, JsonConfig jsonConfig) {
        if (object == null || object.equals("")) {
            return JSONObject.fromObject("{}", jsonConfig);
        }
        return JSONObject.fromObject(object, jsonConfig);
    }

    public static JSONObject getJsonObj(Map<String, Object> map){
        return JSONObject.fromObject(map);
    }

    public static class DefaultJsonEventListenerImpl implements JsonEventListener {
        
        @Override
        public void onArrayEnd() {
        }

        @Override
        public void onArrayStart() {    
        }

        @Override
        public void onElementAdded(int index, Object element) { 
        }

        @Override
        public void onError(JSONException jsone) {
        }

        @Override
        public void onObjectEnd() {
        }

        @Override
        public void onObjectStart() {
        }

        @Override
        public void onPropertySet(String key, Object value, boolean accumulated) {
        }

        @Override
        public void onWarning(String warning) {
        }
    }
}