package com.hx_ai.nlp.simple.query.tuling;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.hx_ai.nlp.simple.query.tuling.*;
import com.hx_ai.nlp.simple.query.tuling.enums.ErrorCodeEnum;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 图灵机器人调用
 */
public class TulingQuery {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String url = "http://openapi.tuling123.com/openapi/api/v2";

    private final String query;

    public TulingQuery(String query) {
        this.query = query;
    }

    public String getTulingResult() {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("reqType", 0);
        requestMap.put("perception", new Perception(new InputText(query), new InputImage(), new SelfInfo(new Location())));
        requestMap.put("userInfo", new UserInfo());

        try {
            String jsonPrettyStr = JSONUtil.toJsonPrettyStr(requestMap);
//            String postResult = HttpUtil.post(url, jsonPrettyStr);
            String postResult = "{\"intent\":{\"actionName\":\"\",\"code\":10037,\"intentName\":\"\"},\"results\":[{\"groupType\":1,\"resultType\":\"text\",\"values\":{\"text\":\"亲，股市深似海，投资需谨慎哦~\"}}]}";
            JSONObject jo = new JSONObject(postResult);
            requestMap.put("data", jo);
            requestMap.put("error", "");
            if(ObjectUtil.isEmpty(jo.get("intent"))){
                requestMap.put("answer", "我有点不太明白。");
            } else {
                JSONObject intentJson = (JSONObject) jo.get("intent");
                if(ObjectUtil.isEmpty(intentJson.get("code"))){
                    requestMap.put("answer", "我有点不太明白。");
                }else{
                    if(ObjectUtil.isNotEmpty(ErrorCodeEnum.getFromErrorCodeEnum(intentJson.getInt("code")))){
                        requestMap.put("answer", "我有点不太明白。");
                    }else{
                        JSONArray successResult = (JSONArray) jo.get("results");
                        if(CollectionUtil.isEmpty(successResult)){
                            requestMap.put("answer", "我有点不太明白。");
                        }else{
                            JSONObject jsonObject = successResult.getJSONObject(0);
                            if(ObjectUtil.isEmpty(jsonObject)){
                                requestMap.put("answer", "我有点不太明白。");
                            }else{
                                JSONObject values = (JSONObject) jsonObject.get("values");
                                if(ObjectUtil.isEmpty(values)){
                                    requestMap.put("answer", "我有点不太明白。");
                                }else{
                                    Object text = values.get("text");
                                    requestMap.put("answer", text);
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception e) {
            requestMap.put("data", new JSONObject());
            requestMap.put("error", "调用接口失败");
            requestMap.put("answer", "我有点不太明白。");
            logger.error("异常:{}", e.getMessage());
        }
        return JSONUtil.toJsonPrettyStr(requestMap);
    }

}
