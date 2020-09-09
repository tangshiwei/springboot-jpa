package com.jpa.base;


import com.google.gson.Gson;
import com.jpa.bean.PUser;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UserUtil {
    private static Gson gson = new Gson();

    public static PUser getUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        PUser user = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user".equals(cookie.getName())) {
                    String value = cookie.getValue();
                    try {
                        String userStr = URLDecoder.decode(value, "utf-8");
                        user = gson.fromJson(userStr, PUser.class);
                        return user;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (user == null) {
            user = new PUser();
            //超级管理员
            user.setId(1L);
            user.setGroupId(1L);
            user.setUserName("administrator");

            //其它管理员用户
//            user.setId(2L);
//            user.setGroupId(2L);
//            user.setUserName("zhangsan");
        }

        return user;
    }

    public static void setUser(PUser bean, HttpServletResponse response) {
        bean.setGroup(null);
        String jsonStr = gson.toJson(bean);
        try {
            String userStr = URLEncoder.encode(jsonStr, "utf-8");

            Cookie cookie = new Cookie("user", userStr);
            cookie.setPath("/");
            cookie.setMaxAge(2 * 60 * 60);
            //response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
