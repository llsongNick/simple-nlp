package com.hx_ai.nlp.simple.query.tuling;

/**
 * @Description [图片信息]
 * @Author lidisong
 * @Date [2020.09.17/16:23]
 * @Version 1.0
 */
public class InputImage {

    private String url = "http://www.baidu.com";

    public InputImage(){
        super();
    }

    public InputImage(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
