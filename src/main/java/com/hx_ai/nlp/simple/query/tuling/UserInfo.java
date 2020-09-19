package com.hx_ai.nlp.simple.query.tuling;

/**
 * @Description [用户参数]
 * @Author lidisong
 * @Date [2020.09.17/16:27]
 * @Version 1.0
 */
public class UserInfo {

    private String apiKey = "2b6973061927463abadc59d4d6daf15b";

    private String userId = "lidisong001";

    public UserInfo(){
        super();
    }

    public UserInfo(String apiKey, String userId){
        this.apiKey = apiKey;
        this.userId = userId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
