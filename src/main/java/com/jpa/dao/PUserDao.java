package com.jpa.dao;

import com.jpa.bean.PUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PUserDao extends JpaRepository<PUser,Long>, JpaSpecificationExecutor<PUser> {

    PUser findByUserNameAndUserPass(String userName, String userPass);
    PUser findByPhoneAndUserPass(String phone, String userPass);
    PUser findByEmailAndUserPass(String phone, String userPass);
}