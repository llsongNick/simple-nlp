package com.hx_ai.nlp.simple.query.tuling;

/**
 * @Description [TODO]
 * @Author lidisong
 * @Date [2020.09.17/17:13]
 * @Version 1.0
 */
public class Location{

    // 所在城市
    private String city = "上海市";
    // 省份
    private String province = "宝山区";
    // 街道
    private String street = "杨泰路";

    public Location(){
        super();
    }

    public Location(String city, String province, String street) {
        this.city = city;
        this.province = province;
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
