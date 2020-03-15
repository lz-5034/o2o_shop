package com.imooc.o2o.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author LZ
 * @date 2020/2/17 22:59:37
 * @description 账号信息
 */
@Data
public class Account {
    private Long accountId;  //账号id
    private String username;    //用户名
    private String password;    //密码
    private Date createTime;    //创建时间
    private Date lastEditTime;  //最后修改时间
    private User user;      //关联外键（用户）

    /*public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }*/
}
