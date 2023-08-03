package com.todo.task.web.userManager.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.todo.task.result.ResultData;
import com.todo.task.web.userManager.bean.UserDto;
import com.todo.task.web.userManager.vo.UserVo;
import com.todo.task.web.userManager.dto.UserSearchDto;
import com.todo.task.web.userManager.entity.User;
import com.todo.task.web.userManager.vo.UserPageVo;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author 樊小凯
 * @Date 2023/07/27/12:23
 * @Description:
 */
public interface UserService extends IService<User> {

    /**
     * 用户信息列表查询
     * @param searchDto 查询条件
     * @return IPage<UserPageVo>
     */
    IPage<UserPageVo> queryUserList(UserSearchDto searchDto);

    /**
     * 用户注册
     * @param userDto 注册信息
     * @return ResultData
     */
    ResultData register(UserDto userDto);

    /**
     * 根据账号查询用户信息
     * @param account 账号
     * @return UserVo
     */
    UserVo getUserInfoByAccount(String account);
}
