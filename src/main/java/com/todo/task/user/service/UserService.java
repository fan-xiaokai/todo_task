//package com.todo.task.user.service;
//
//
//
//import com.todo.task.result.ResultData;
//import com.todo.task.user.bean.UserDto;
//import com.todo.task.user.entity.User;
//
//import java.util.List;
//
///**
// * Created with IntelliJ IDEA.
// *
// * @Author 樊小凯
// * @Date 2023/04/16/19:57
// * @Description:
// */
//public interface UserService {
//
//    /**
//     * 用户插入
//     *
//     * @param userDto 接受用户信息dto
//     * @return ResultData
//     */
//    ResultData insert(UserDto userDto);
//
//    /**
//     * 查询所有用户信息
//     *
//     * @param userDto 查询条件
//     * @return ResultData<List < User>>
//     */
//    ResultData<List<User>> findAll(UserDto userDto);
//
//    /**
//     * 通过用户id查询
//     *
//     * @param userDto 查询条件
//     * @return ResultData<User>
//     */
//    ResultData<User> findById(UserDto userDto);
//
//    /**
//     * 更改
//     *
//     * @param userDto 用户信息
//     * @return ResultData
//     */
//    ResultData update(UserDto userDto);
//}
