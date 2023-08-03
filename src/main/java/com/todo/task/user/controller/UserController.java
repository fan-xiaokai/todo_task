//package com.todo.task.user.controller;
//
//import com.todo.task.result.ResultData;
//import com.todo.task.user.bean.UserDto;
//import com.todo.task.user.entity.User;
//import com.todo.task.user.service.UserService;
//import io.swagger.annotations.Api;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * Created with IntelliJ IDEA.
// *
// * @Author 樊小凯
// * @Date 2023/04/16/19:55
// * @Description: mongo CRUD测试 Controller
// */
//@Api(tags = "用户管理")
//@RestController
//@RequestMapping("/userManager/")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("insert")
//    public ResultData insert(@RequestBody UserDto userDto) {
//        return userService.insert(userDto);
//    }
//
//    @GetMapping("findAll")
//    private ResultData<List<User>> findAll(UserDto userDto) {
//        return userService.findAll(userDto);
//    }
//
//    @GetMapping("findById")
//    private ResultData<User> findById(UserDto userDto) {
//        return userService.findById(userDto);
//    }
//
//    @PostMapping("update")
//    public ResultData update(@RequestBody UserDto userDto) {
//        return userService.update(userDto);
//    }
//}
