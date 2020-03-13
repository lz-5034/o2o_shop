package com.imooc.o2o.entity;

import java.util.Date;

/**
 * @author LZ
 * @date 2020/2/17 22:02:36
 * @description 用户信息
 */
public class User {
    private Long userId;        //用户id
    private String name;        //用户姓名
    private String profileImg;  //用户头像地址
    private String email;       //邮箱
    private String sex;         //性别
    private Integer enableStatus;//用户权利：0.禁止使用商城 1.允许使用商城
    private Integer userType;   //用户类型：1.顾客 2.商家 3.超级管理员
    private Date createTime;    //创建时间
    private Date lastEditTime;  //最后修改时间

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
}
