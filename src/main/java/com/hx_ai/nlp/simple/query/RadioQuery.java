package com.hx_ai.nlp.simple.query;

import com.hx_ai.nlp.simple.utils.RadioProperties;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 收音机查询
 */
public class RadioQuery {

    private final RadioProperties properties = new RadioProperties();
    private final String query;
    private String operation = "";
    private String channel = "";

    private String getChOrOP(Map<String, List<String>> data) {
        for (Map.Entry<String, List<String>> entry : data.entrySet()) {
            List<String> dataAlias = entry.getValue();
            for (String alias : dataAlias)
                if (query.contains(alias))
                    return entry.getKey();
        }
        return "";
    }

    public RadioQuery(String query) {
        this.query = query;
    }

    public boolean isRadio() {

        // 是否存在电台
        Map<String, List<String>> radios = properties.getRadios();
        channel = getChOrOP(radios);

        // 判断操作
        Map<String, List<String>> operations = properties.getOperations();
        operation = getChOrOP(operations);

        if(channel.equals("")) { // 如果不存在频道
            return !operation.equals("");
        } else { // 如果存在频道
            if(operation.equals(""))  // 如果不存在指令
                operation = "START";
            return true;
        }
    }

    public String getRadioResult() {
        JSONObject json = new JSONObject();
        json.put("rc", 0);
        json.put("error", "");
        json.put("text", query);
        json.put("service", "radio");
        json.put("operation", operation);
        json.put("semantic", new JSONObject().put("channel", channel));
        return json.toString();
    }
}
