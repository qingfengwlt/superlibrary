package com.superlibrary.ken.bean;

/**
 * Created by PC_WLT on 2017/4/26.
 */

public class ComBean {

    private String id;
    private String msg;
    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ComBean{" +
                "id='" + id + '\'' +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
