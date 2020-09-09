package com.jpa.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtil {

    /**
     * 检测手机号码
     */
    public static boolean checkPhone(String phone) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        if (phone.length() == 11 || phone.length() == 8) {

            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (isMatch) {
                // System.out.println("您的手机号" + phone + "是正确格式");
                return false;
            }
        }
        return false;
    }
}
