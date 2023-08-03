//package com.todo.task.user.service.impl;
//
//import cn.hutool.core.bean.BeanUtil;
//import cn.hutool.core.bean.copier.CopyOptions;
//import com.todo.task.result.ResultData;
//import com.todo.task.user.bean.UserDto;
//import com.todo.task.user.entity.User;
//import com.todo.task.user.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Update;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * Created with IntelliJ IDEA.
// *
// * @Author 樊小凯
// * @Date 2023/04/16/19:58
// * @Description:
// */
//@Service
//public class UserServiceImpl implements UserService {
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @Override
//    public ResultData insert(UserDto userDto) {
//
//        User user = new User();
//        BeanUtil.copyProperties(userDto, user, CopyOptions.create().setIgnoreNullValue(true));
//
//        mongoTemplate.insert(user);
//        return ResultData.successWithMessage();
//    }
//
//    @Override
//    public ResultData<List<User>> findAll(UserDto userDto) {
//
//        return ResultData.success(mongoTemplate.findAll(User.class));
//    }
//
//    @Override
//    public ResultData<User> findById(UserDto userDto) {
//
//        return ResultData.success(mongoTemplate.findById(userDto.getId(), User.class));
//    }
//
//    @Override
//    public ResultData update(UserDto userDto) {
//        Query query = Query.query(Criteria.where("_id").is(userDto.getId()));
//        Update update = new Update();
//        update.inc("fansNum");
//        mongoTemplate.updateMulti(query, update, "user");
//        return ResultData.successWithMessage();
//    }
//}
