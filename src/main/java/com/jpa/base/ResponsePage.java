package com.jpa.base;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponsePage extends PageBean implements Serializable {
    private Integer code;
    private String msg;
    private Object data;
}
