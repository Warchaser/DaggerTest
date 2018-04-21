package com.warchaser.daggertest.moshi;

public class BaseRespBean {

    private int state;

    @Parse2String
    private String data;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
