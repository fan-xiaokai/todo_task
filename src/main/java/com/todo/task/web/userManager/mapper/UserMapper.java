package com.todo.task.web.userManager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.todo.task.web.userManager.vo.UserVo;
import com.todo.task.web.userManager.dto.UserSearchDto;
import com.todo.task.web.userManager.entity.User;
import com.todo.task.web.userManager.vo.UserPageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author 樊小凯
 * @Date 2023/07/27/12:25
 * @Description:
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 用户信息分页查询
     *
     * @param page      条件
     * @param searchDto 查询条件
     * @return IPage<UserPageVo>
     */
    IPage<UserPageVo> queryUserListPage(@Param("page") Page page, @Param("searchDto") UserSearchDto searchDto);

    /**
     * 根据账号查询用户信息
     *
     * @param account 账号
     * @return UserVo
     */
    UserVo getUserInfoByAccount(@Param("account") String account);
}
