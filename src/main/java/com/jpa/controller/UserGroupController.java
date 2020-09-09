package com.jpa.controller;

import com.jpa.base.BaseController;
import com.jpa.base.ResponseData;
import com.jpa.base.UserUtil;
import com.jpa.bean.PUser;
import com.jpa.bean.PUserGroup;
import com.jpa.service.PUserGroupService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/pm")
public class UserGroupController extends BaseController {
    @Autowired
    private PUserGroupService userGroupService;

    @PostMapping(value = "/addUserGroup", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData addBean(HttpServletRequest request, PUserGroup bean) {
        try {
            PUser user = UserUtil.getUser(request);

            if (StringUtils.isBlank(bean.getName())) {
                return new ResponseData(CODE_NOPARAM, MSG_NOPARAM + ":name");
            }

            List<PUserGroup> list = userGroupService.findBean(bean);
            if(list!=null&&list.size()>0){
                return new ResponseData(CODE_DATAEXITS, MSG_DATAEXITS +bean.getName());
            }

            bean.setCreateBy(user.getUserName());
            bean.setUpdateBy(user.getUserName());
            bean.setCreateTime(new Date());
            bean.setUpdateTime(new Date());

            userGroupService.addBean(bean);

            return new ResponseData(CODE_SUCCESS, MSG_SUCCESS);
        } catch (Exception e) {
            log.error("/addUserGroup()", e);
            return new ResponseData(CODE_EXCPTION, MSG_EXCPTION);
        }

    }


    @PostMapping(value = "/updateUserGroup", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData updateBean(HttpServletRequest request, PUserGroup bean) {
        try {
            PUser user = UserUtil.getUser(request);

            bean.setUpdateBy(user.getUserName());
            bean.setUpdateTime(new Date());

            userGroupService.updateBean(bean);

            return new ResponseData(CODE_SUCCESS, MSG_SUCCESS);
        } catch (Exception e) {
            log.error("/updateUserGroup()", e);
            return new ResponseData(CODE_EXCPTION, MSG_EXCPTION);
        }

    }

    @PostMapping(value = "/deleteUserGroup", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData deleteBean(@RequestParam(name = "id") Long id) {
        try {
            userGroupService.deleteBean(id);
            return new ResponseData(CODE_SUCCESS, MSG_SUCCESS);
        } catch (Exception e) {
            log.error("/deleteUserGroup()", e);
            return new ResponseData(CODE_EXCPTION, MSG_EXCPTION);
        }
    }

    @GetMapping(value = "/findUserGroup", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData findBean(PUserGroup bean) {
        try {
            bean.setStatus(1L);
            List<PUserGroup> list = userGroupService.findBean(bean);

            return new ResponseData(CODE_SUCCESS, MSG_SUCCESS, list);
        } catch (Exception e) {
            log.error("/findUserGroup()", e);
            return new ResponseData(CODE_EXCPTION, MSG_EXCPTION);
        }
    }

    @GetMapping(value = "/searchUserGroup", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseData searchBean(
            @RequestParam(name = "name", required = false) String name,

            @RequestParam(name = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        try {
            PUserGroup bean = new PUserGroup();
            if(StringUtils.isNotBlank(name)){
                bean.setName(name);
            }

            Page<PUserGroup> page = userGroupService.searchBean(bean, (pageIndex - 1) * pageSize, pageSize);

            return new ResponseData(CODE_SUCCESS, MSG_SUCCESS, page.getContent(), page.getTotalElements());

        } catch (Exception e) {
            log.error("/searchUserGroup()", e);
            return new ResponseData(CODE_EXCPTION, MSG_EXCPTION);
        }
    }

}
