package com.jpa.dao;

import com.jpa.bean.PUserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PUserGroupDao extends JpaRepository<PUserGroup,Long>, JpaSpecificationExecutor<PUserGroup> {

}