package com.hx_ai.nlp.simple.query.tuling;

/**
 * @Description [输入信息]
 * @Author lidisong
 * @Date [2020.09.17/16:29]
 * @Version 1.0
 */
public class Perception {

    private InputText inputText;

    private InputImage inputImage;

    private SelfInfo selfInfo;

    public Perception(InputText inputText, SelfInfo selfInfo) {
        this.inputText = inputText;
        this.selfInfo = selfInfo;
    }

    public Perception(InputImage inputImage, SelfInfo selfInfo) {
        this.inputImage = inputImage;
        this.selfInfo = selfInfo;
    }

    public Perception(InputText inputText, InputImage inputImage, SelfInfo selfInfo){
        this.inputText = inputText;
        this.inputImage = inputImage;
        this.selfInfo = selfInfo;
    }

    public InputText getInputText() {
        return inputText;
    }

    public void setInputText(InputText inputText) {
        this.inputText = inputText;
    }

    public InputImage getInputImage() {
        return inputImage;
    }

    public void setInputImage(InputImage inputImage) {
        this.inputImage = inputImage;
    }

    public SelfInfo getSelfInfo() {
        return selfInfo;
    }

    public void setSelfInfo(SelfInfo selfInfo) {
        this.selfInfo = selfInfo;
    }
}
