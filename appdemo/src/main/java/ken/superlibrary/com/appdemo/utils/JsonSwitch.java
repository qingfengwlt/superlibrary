package ken.superlibrary.com.appdemo.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author leitao
 * @fileName pkg.shi.com.util.config
 * @date 2016/4/14
 * @update by     on
 */
public class JsonSwitch {
    public static String toJson(Map<String,String> map){
        Set<String> keys = map.keySet();
        String key = "";
        String value = "";
        StringBuffer jsonBuffer = new StringBuffer();
        jsonBuffer.append("{");
        for(Iterator<String> it = keys.iterator();it.hasNext();){
            key =  (String)it.next();
            value = map.get(key);
            jsonBuffer.append("\"");
            jsonBuffer.append(key+"\":\""+value+"\"");
            if(it.hasNext()){
                jsonBuffer.append(",");
            }
        }
        jsonBuffer.append("}");
        return jsonBuffer.toString();
    }

    public static String toJson2(Map<String,String> map){
        Set<Map.Entry<String, String>> entrys = map.entrySet();
        Map.Entry<String, String> entry = null;
        String key = "";
        String value = "";
        StringBuffer jsonBuffer = new StringBuffer();
        jsonBuffer.append("{");
        for(Iterator<Map.Entry<String, String>> it = entrys.iterator();it.hasNext();){
            entry =  (Map.Entry<String, String>)it.next();
            key = entry.getKey();
            value = entry.getValue();
            jsonBuffer.append(key+":"+value);
            if(it.hasNext()){
                jsonBuffer.append(",");
            }
        }
        jsonBuffer.append("}");
        return jsonBuffer.toString();
    }

}
