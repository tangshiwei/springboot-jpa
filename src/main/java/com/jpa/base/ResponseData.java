package com.jpa.base;

import lombok.Data;
import java.io.Serializable;

@Data
public class ResponseData implements Serializable {
    private int code;
    private String msg;
    private Object data;
    private Long count;

    public ResponseData() {
    }

    public ResponseData(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseData(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseData(int code, String msg, Object data, Long count) {
        this.code=code;
        this.msg=msg;
        this.data=data;
        this.count=count;
    }
}
