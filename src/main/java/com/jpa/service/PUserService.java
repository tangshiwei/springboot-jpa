package com.jpa.service;

import com.jpa.base.BaseService;
import com.jpa.base.PhoneUtil;
import com.jpa.bean.PUser;
import com.jpa.dao.PUserDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PUserService implements BaseService<PUser> {

    @Autowired
    private PUserDao userDao;

    @Override
    public void addBean(PUser bean) {
        userDao.save(bean);
    }

    @Override
    public void updateBean(PUser source) {
        PUser target = findBeanById(source.getId());
        convertBean(source, target);

        userDao.saveAndFlush(target);
    }

    @Override
    public void deleteBean(Long id) {
        userDao.deleteById(id);
    }


    @Override
    public PUser findBeanById(Long id) {
        Optional<PUser> optional = userDao.findById(id);
        if (optional.isPresent())
            return optional.get();
        return null;
    }

    @Override
    public List<PUser> findBean(PUser bean) {

        ExampleMatcher matcher = getExampleMatcher(bean,false);
        Example<PUser> example = Example.of(bean, matcher);
        List<PUser> list = userDao.findAll(example);

        return list;
    }

    @Override
    public Page<PUser> searchBean(PUser bean, Integer pageIndex, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
        Page<PUser> page = null;

        ExampleMatcher matcher = getExampleMatcher(bean,true);
        Example<PUser> example = Example.of(bean, matcher);
        page = userDao.findAll(example, pageRequest);

        return page;
    }

    @Override
    public ExampleMatcher getExampleMatcher(PUser bean, boolean contains) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        if (bean.getGroupId() != null) {
            matcher = matcher.withMatcher("groupId", ExampleMatcher.GenericPropertyMatchers.storeDefaultMatching());
        }
        if (StringUtils.isNotBlank(bean.getUserName())) {
            if (contains)
                matcher = matcher.withMatcher("userName", ExampleMatcher.GenericPropertyMatchers.contains());
            else
                matcher = matcher.withMatcher("userName", ExampleMatcher.GenericPropertyMatchers.storeDefaultMatching());
        }
        if (StringUtils.isNotBlank(bean.getPhone())) {
            if (contains)
                matcher = matcher.withMatcher("phone", ExampleMatcher.GenericPropertyMatchers.contains());
            else
                matcher = matcher.withMatcher("phone", ExampleMatcher.GenericPropertyMatchers.storeDefaultMatching());
        }
        if (StringUtils.isNotBlank(bean.getEmail())) {
            if (contains)
                matcher = matcher.withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains());
            else
                matcher = matcher.withMatcher("email", ExampleMatcher.GenericPropertyMatchers.storeDefaultMatching());
        }
        if (bean.getStatus() != null) {
            matcher = matcher.withMatcher("status", ExampleMatcher.GenericPropertyMatchers.storeDefaultMatching());
        }
        return matcher;
    }

    public PUser login(String userName, String userPass) {
        PUser user = null;
        if (userName.contains("@")) {
            user = userDao.findByEmailAndUserPass(userName, userPass);
        } else if (PhoneUtil.checkPhone(userName)) {
            user = userDao.findByPhoneAndUserPass(userName, userPass);
        } else {
            user = userDao.findByUserNameAndUserPass(userName, userPass);
        }

        return user;
    }

}
