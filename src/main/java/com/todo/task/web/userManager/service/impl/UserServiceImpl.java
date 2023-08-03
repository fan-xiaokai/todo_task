package com.todo.task.web.userManager.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.todo.task.result.ResultData;
import com.todo.task.utils.Sha256Util;
import com.todo.task.web.userManager.bean.UserDto;
import com.todo.task.web.userManager.vo.UserVo;
import com.todo.task.web.userManager.dto.UserSearchDto;
import com.todo.task.web.userManager.entity.User;
import com.todo.task.web.userManager.mapper.UserMapper;
import com.todo.task.web.userManager.service.UserService;
import com.todo.task.web.userManager.vo.UserPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author 樊小凯
 * @Date 2023/07/27/12:25
 * @Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public IPage<UserPageVo> queryUserList(UserSearchDto searchDto) {

        Page page = new Page();
        page.setCurrent(searchDto.getPage());
        page.setSize(searchDto.getLimit());
        return userMapper.queryUserListPage(page, searchDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData register(UserDto userDto) {

        UserVo userVo = this.getUserInfoByAccount(userDto.getAccount());
        if (userVo != null) {
            throw new ApiException("账号已存在!");
        }

        User user = new User();
        BeanUtil.copyProperties(userDto,user, CopyOptions.create().setIgnoreNullValue(true));

        String salt = RandomUtil.randomString(8);
        user.setSalt(salt);
        user.setPassword(Sha256Util.getSha256(user.getPassword(),salt));

        this.saveOrUpdate(user);
        return ResultData.successWithMessageNoData();
    }

    @Override
    public UserVo getUserInfoByAccount(String account) {
        return userMapper.getUserInfoByAccount(account);
    }
}
