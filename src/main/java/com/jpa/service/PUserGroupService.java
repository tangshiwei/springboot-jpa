package com.jpa.service;

import com.jpa.base.BaseService;
import com.jpa.bean.PUserGroup;
import com.jpa.dao.PUserGroupDao;
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
public class PUserGroupService implements BaseService<PUserGroup> {

    @Autowired
    private PUserGroupDao userGroupDao;

    @Override
    public void addBean(PUserGroup bean) {
        userGroupDao.save(bean);
    }

    @Override
    public void updateBean(PUserGroup bean) {
        PUserGroup target = findBeanById(bean.getId());
        convertBean(bean, target);
        userGroupDao.saveAndFlush(target);
    }

    @Override
    public void deleteBean(Long id) {
        userGroupDao.deleteById(id);
    }


    @Override
    public PUserGroup findBeanById(Long id) {
        Optional<PUserGroup> optional = userGroupDao.findById(id);
        if (optional.isPresent())
            return optional.get();
        return null;
    }

    @Override
    public List<PUserGroup> findBean(PUserGroup bean) {
        ExampleMatcher matcher = getExampleMatcher(bean,false);
        Example<PUserGroup> example = Example.of(bean, matcher);
        List<PUserGroup> list = userGroupDao.findAll(example);

        return list;
    }

    @Override
    public Page<PUserGroup> searchBean(PUserGroup bean, Integer pageIndex, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);

        ExampleMatcher matcher = getExampleMatcher(bean,true);
        Example<PUserGroup> example = Example.of(bean, matcher);
        Page<PUserGroup> page = userGroupDao.findAll(example, pageRequest);

        return page;
    }

    @Override
    public ExampleMatcher getExampleMatcher(PUserGroup bean, boolean contains) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        if (StringUtils.isNotBlank(bean.getName())) {
            if (contains)
                matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
            else
                matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.storeDefaultMatching());
        }
        return matcher;
    }

}

