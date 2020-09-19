package com.hx_ai.nlp.simple.query.tuling;

import com.hx_ai.nlp.simple.utils.HttpClientUtil;
import org.json.JSONObject;

/**
 * Created by dzkan on 2016/11/18.
 * 图灵机器人调用
 */
public class TulingQueryBak {

    private static final String apiKey = "ddbd649e859f4f3e9ea52a8d0b4a05ab";
    private static final String template = "http://www.tuling123.com/openapi/api?key=%s&info=%s&userid=dzkang2011";

    private String query;

    public TulingQueryBak(String query) {
        this.query = query;
    }

    public String getTulingResult() {
        JSONObject json = new JSONObject();
        json.put("rc", 0);
        json.put("text", query);
        json.put("service", "tuling");
        json.put("operation", "SEARCH");

        try {
            String html = HttpClientUtil.grabHtmlOncePost(String.format(template, apiKey, query));
            JSONObject jo = new JSONObject(html);
            json.put("data", jo);
            json.put("error", "");
            if((int)jo.get("code") != 100000) {
                json.put("answer", "我有点不太明白。");
            } else {
                json.put("answer", jo.get("text"));
            }
        }catch (Exception e) {
            json.put("data", new JSONObject());
            json.put("error", "调用接口失败");
            json.put("answer", "我有点不太明白。");
        }
        return json.toString();
    }

}
