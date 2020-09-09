package com.jpa.bean;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "p_user_group")
public class PUserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;


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

}
