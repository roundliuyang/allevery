package com.yly.allevery.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 用户 DO
 */

//  @TableName 注解用来将指定的数据库表和 JavaBean 进行映射。该注解属性如下：
//  value 字符串类型，不是必填，用来指定数据表名称。例如：
//  @TableName("user")
//  public class UserBean {
//    // ...
//  }
//  上面实例中，将 user 数据表和 UserBean 实体进行映射。
@TableName(value = "users")
public class UserDO {

    /**
     * 用户编号
     */
    private Integer id;
    /**
     * 账号
     */
    private String username;
    /**
     * 密码（明文）
     *
     * ps：生产环境下，千万不要明文噢
     */
    private String password;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否删除
     */
    @TableLogic
    private Integer deleted;

    public Integer getId() {
        return id;
    }

    public UserDO setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserDO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDO setPassword(String password) {
        this.password = password;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public UserDO setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public UserDO setDeleted(Integer deleted) {
        this.deleted = deleted;
        return this;
    }
}
