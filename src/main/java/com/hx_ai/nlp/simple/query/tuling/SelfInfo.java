package com.hx_ai.nlp.simple.query.tuling;

/**
 * @Description [客户端属性]
 * @Author lidisong
 * @Date [2020.09.17/16:23]
 * @Version 1.0
 */
public class SelfInfo {

    private Location location;

    public SelfInfo(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
