package com.jpa.bean;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "p_user")
public class PUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "group_id")
    private Long groupId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_pass")
    private String userPass;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "image")
    private String image;

    @Column(name = "create_time")
    private java.util.Date createTime;
    @Column(name = "create_by")
    private String createBy;
    @Column(name = "update_time")
    private java.util.Date updateTime;
    @Column(name = "update_by")
    private String updateBy;
    @Column(name = "status")
    private Long status;

    @ManyToOne
    @JoinColumn(name="group_id",insertable = false, updatable = false)
    private PUserGroup group;

    //超级管理员
    public static final Long GROUP_ADMINISTATOR=1L;
    //管理员
    public static final Long GROUP_ADMIN=2L;
    //普通用户
    public static final Long GROUP_user=3L;

}
