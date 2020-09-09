package com.jpa.base;

import com.jpa.node.MenuNode;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseNode implements Serializable {
    private int code;
    private String msg;
    //主页
    private MenuNode home;
    //logo
    private MenuNode logo;
    //数据
    private Object data;
    private Long count;

    public ResponseNode() {
    }

    public ResponseNode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseNode(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseNode(int code, String msg, Object data, Long count) {
        this.code=code;
        this.msg=msg;
        this.data=data;
        this.count=count;
    }
}
