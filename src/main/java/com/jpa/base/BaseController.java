package com.jpa.base;


import com.google.gson.Gson;

import java.text.SimpleDateFormat;

public  class BaseController {

    public static final int CODE_SUCCESS = 0;
    public static final int CODE_FAILD = -1;
    public static final int CODE_DATAEXITS = 201;
    public static final int CODE_NODATA = 300;
    public static final int CODE_NOPARAM = 400;
    public static final int CODE_EXCPTION = 500;

    public static final int CODE_SMS_CODE_ERROR = 600;
    //
    public static final String MSG_SUCCESS = "操作成功";
    public static final String MSG__FAILD="操作失败";
    public static final String MSG_DATAEXITS = "数据已存在: ";
    public static final String MSG_NODATA = "没有数据";
    public static final String MSG_NOPARAM = "缺少参数: ";
    public static final String MSG_EXCPTION = "系统异常: ";

    public static final String MSG_SMS_CODE_ERROR = "验证码错误";

    public static SimpleDateFormat dateTimeFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm:ss");
    public Gson gson=new Gson();

}
