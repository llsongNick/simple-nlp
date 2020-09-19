package com.hx_ai.nlp.simple.query.tuling;

/**
 * @Description [文本信息]
 * @Author lidisong
 * @Date [2020.09.17/16:22]
 * @Version 1.0
 */
public class InputText {

    private String text = "我饿了~";

    public InputText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
