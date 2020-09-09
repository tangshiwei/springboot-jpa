package com.jpa.controller;

import com.jpa.base.BaseController;
import com.jpa.base.ResponseData;
import com.jpa.base.UserUtil;
import com.jpa.bean.PUser;
import com.jpa.service.PUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/pm")
public class UserController extends BaseController {
    @Autowired
    private PUserService userService;

    /**
     * 登录
     */
    @RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData login(
            @RequestParam(name = "userName") String userName,
            @RequestParam(name = "userPass") String userPass) {
        try {

            PUser user = userService.login(userName, userPass);
            if (user == null) {
                return new ResponseData(CODE_NODATA, "用户名或者密码错误");
            }
            if(user.getStatus()==0){
                return new ResponseData(CODE_NODATA, "用户审核中");
            }
            if(user.getStatus()==2){
                return new ResponseData(CODE_NODATA, "用户已禁用");
            }

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            HttpServletResponse response = attributes.getResponse();

            UserUtil.setUser(user,response);

            return new ResponseData(CODE_SUCCESS, MSG_SUCCESS, user);

        } catch (Exception e) {
            log.error("/login()", e);
            return new ResponseData(CODE_EXCPTION, MSG_EXCPTION);
        }
    }
    /**
     *发送验证码
     */
    @PostMapping(value = "/sendSmsCode", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData sendSmsCode(
            @RequestParam(name = "phone") String  phone) {
        ResponseData data=new ResponseData();
        try {
            //生成验证码
            String code="123456";
            //保存验证码
            data.setCode(CODE_SUCCESS);
            data.setMsg(MSG_SUCCESS);
            data.setData(code);

            return data;
        } catch (Exception e) {
            log.error("/sendSmsCode()", e);
            return new ResponseData(CODE_EXCPTION, MSG_EXCPTION);
        }

    }
    /**
     *效验验证码
     */
    @PostMapping(value = "/checkSmsCode", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData checkCode(
            @RequestParam(name = "phone") String  phone,
            @RequestParam(name = "code") String  code) {
        try {
            if("15766982435".equals(phone)&&"123456".equals(code)){
                return new ResponseData(CODE_SUCCESS, MSG_SUCCESS);
            }

            return new ResponseData(CODE_SMS_CODE_ERROR, MSG_SMS_CODE_ERROR);
        } catch (Exception e) {
            log.error("/checkSmsCode()", e);
            return new ResponseData(CODE_EXCPTION, MSG_EXCPTION);
        }

    }


    /**
     * 登出
     */
    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData logout(@RequestParam(name = "id") Long id) {
        try {
            //清理缓存数据
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            HttpServletResponse response = attributes.getResponse();

            UserUtil.setUser(new PUser(),response);

            return new ResponseData(CODE_SUCCESS, MSG_SUCCESS);
        } catch (Exception e) {
            log.error("/addUser()", e);
            return new ResponseData(CODE_EXCPTION, MSG_EXCPTION);
        }

    }

    @PostMapping(value = "/addUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData addBean(HttpServletRequest request, PUser bean) {
        try {
            PUser user = UserUtil.getUser(request);
            if (bean.getGroupId() == null) {
                return new ResponseData(CODE_NOPARAM, MSG_NOPARAM + "groupId");
            }

            if ( StringUtils.isBlank(bean.getPhone()) ) {
                return new ResponseData(CODE_NOPARAM, MSG_NOPARAM + "phone");
            }

            if (StringUtils.isBlank(bean.getUserPass())) {
                return new ResponseData(CODE_NOPARAM, MSG_NOPARAM + "userPass");
            }

            List<PUser> list = userService.findBean(bean);
            if (list != null && list.size() > 0) {
                return new ResponseData(CODE_DATAEXITS, MSG_DATAEXITS + bean.getUserName());
            }

            bean.setCreateBy(user.getUserName());
            bean.setUpdateBy(user.getUserName());
            bean.setCreateTime(new Date());
            bean.setUpdateTime(new Date());
            bean.setStatus(0L);
            userService.addBean(bean);

            return new ResponseData(CODE_SUCCESS, MSG_SUCCESS);
        } catch (Exception e) {
            log.error("/addUser()", e);
            return new ResponseData(CODE_EXCPTION, MSG_EXCPTION);
        }
    }


    @PostMapping(value = "/updateUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData updateBean(HttpServletRequest request, PUser bean) {
        try {
            PUser user = UserUtil.getUser(request);
            if (bean.getId() == null) {
                return new ResponseData(CODE_NOPARAM, MSG_NOPARAM);
            }

            bean.setUpdateBy(user.getUserName());
            bean.setUpdateTime(new Date());
            userService.updateBean(bean);
            return new ResponseData(CODE_SUCCESS, MSG_SUCCESS);
        } catch (Exception e) {
            log.error("/updateUser()", e);
            return new ResponseData(CODE_EXCPTION, MSG_EXCPTION);
        }

    }

    @PostMapping(value = "/deleteUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData deleteBean(@RequestParam(name = "id") Long id) {
        try {
            userService.deleteBean(id);
            return new ResponseData(CODE_SUCCESS, MSG_SUCCESS);
        } catch (Exception e) {
            log.error("/deleteUser()", e);
            return new ResponseData(CODE_EXCPTION, MSG_EXCPTION);
        }
    }

    @GetMapping(value = "/findUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData findBean(PUser bean) {
        try {
            bean.setStatus(1L);
            List<PUser> list = userService.findBean(bean);

            return new ResponseData(CODE_SUCCESS, MSG_SUCCESS, list);
        } catch (Exception e) {
            log.error("/findUser()", e);
            return new ResponseData(CODE_EXCPTION, MSG_EXCPTION);
        }
    }

    @GetMapping(value = "/searchUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData searchBean(
            HttpServletRequest request,
            @RequestParam(name = "groupId", required = false) Long groupId,
            @RequestParam(name = "status", required = false) Long status,
            @RequestParam(name = "userName", required = false) String userName,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        try {

            PUser bean = new PUser();
            bean.setGroupId(groupId);
            bean.setStatus(status);
            if (StringUtils.isNotBlank(userName))
                bean.setUserName(userName);
            if (StringUtils.isNotBlank(phone))
                bean.setPhone(phone);
            if (StringUtils.isNotBlank(email))
                bean.setEmail(email);

            Page<PUser> page = userService.searchBean(bean, (pageIndex - 1) * pageSize, pageSize);

            return new ResponseData(CODE_SUCCESS, MSG_SUCCESS, page.getContent(), page.getTotalElements());

        } catch (Exception e) {
            log.error("/searchUser()", e);
            return new ResponseData(CODE_EXCPTION, MSG_EXCPTION);
        }
    }

}
