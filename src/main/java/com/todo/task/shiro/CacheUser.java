package com.todo.task.shiro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author 樊小凯
 * @Date 2023/07/30/15:30
 * @Description: 缓存对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CacheUser implements Serializable {

    /**
     * 用户id
     */
    private String id;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 账号
     */
    private String account;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 登录token
     */
    private String token;

    /**
     *  头像地址
     */
    private String avatarAddress;
}
