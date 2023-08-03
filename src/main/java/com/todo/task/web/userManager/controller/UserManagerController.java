package com.todo.task.web.userManager.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.todo.task.result.ResultData;
import com.todo.task.shiro.CacheUser;
import com.todo.task.web.userManager.bean.LoginUserDto;
import com.todo.task.web.userManager.bean.UserDto;
import com.todo.task.web.userManager.vo.UserVo;
import com.todo.task.web.userManager.dto.UserSearchDto;
import com.todo.task.web.userManager.service.UserService;
import com.todo.task.web.userManager.vo.UserPageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * Created with IntelliJ IDEA.
 *
 * @Author 樊小凯
 * @Date 2023/07/27/12:22
 * @Description: 用户管理接口
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user/")
public class UserManagerController {

    @Autowired
    private UserService userService;

    @ApiOperation("获取用户信息")
    @GetMapping("queryUserList")
    public ResultData<IPage<UserPageVo>> queryUserList(UserSearchDto searchDto){
        return ResultData.success(userService.queryUserList(searchDto));
    }

    @ApiOperation("用户注册")
    @PostMapping("register")
    public ResultData register(@RequestBody @Validated UserDto userDto){
        return userService.register(userDto);
    }


    @ApiOperation("登录")
    @PostMapping("login")
    public ResultData login(@RequestBody @Validated LoginUserDto loginUserDto){

        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginUserDto.getAccount(), loginUserDto.getPassword());
        token.setRememberMe(true);
        currentUser.login(token);
        UserVo userVo= (UserVo)currentUser.getPrincipals().getPrimaryPrincipal();
        CacheUser cacheUser = CacheUser.builder()
                .token(currentUser.getSession().getId().toString())
                .build();
        BeanUtil.copyProperties(userVo, cacheUser, CopyOptions.create().setIgnoreNullValue(true));
        SecurityUtils.getSubject().getSession().setTimeout(1000 * 3600 * 24 * 7);

        return ResultData.success(cacheUser);
    }

    @ApiOperation(value = "退出登录")
    @GetMapping("/logout")
    public Object logOut() {
        UserVo userVo = (UserVo) SecurityUtils.getSubject().getPrincipal();
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResultData.successWithMessage("退出成功!");
    }


}
