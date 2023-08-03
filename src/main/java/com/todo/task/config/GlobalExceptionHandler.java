package com.todo.task.config;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.todo.task.result.ResultData;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author xtf
 * @Date 06/06/2020 09:30
 * @Description 通用 Api Controller 全局异常处理
 * @Version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


//    /**
//     * <p>
//     * 未登录异常
//     * <p>
//     *
//     * @param e 异常类型
//     * @return
//     */
//    @ExceptionHandler(value = UnauthenticatedException.class)
//    public ResponseData notLogin(HttpServletRequest req, Exception e) {
//        return ResponseData.errorNoLogin();
//    }
//
//    /**
//     * <p>
//     * 无权限异常
//     * <p>
//     *
//     * @param e 异常类型
//     * @return
//     */
//    @ExceptionHandler(value = UnauthorizedException.class)
//    public ResponseData noAuth(HttpServletRequest req, Exception e) {
//        return ResponseData.errorNoPermit();
//    }

    /**
     * <p>
     * 用户名不存在
     * <p>
     *
     * @param e 异常类型
     * @return
     */
    @ExceptionHandler(value = UnknownAccountException.class)
    public ResultData noAccount(HttpServletRequest req, Exception e) {
        return ResultData.fail("用户名不存在");
    }

    /**
     * <p>
     * 用户名重复
     * <p>
     *
     * @param e 异常类型
     * @return
     */
    @ExceptionHandler(value = AuthenticationException.class)
    public ResultData repeatAccount(HttpServletRequest req, Exception e) {
        return ResultData.fail("用户不存在");
    }
//
//    /**
//     * <p>
//     * 您的账户已经被冻结
//     * <p>
//     *
//     * @param e 异常类型
//     * @return
//     */
//    @ExceptionHandler(value = DisabledAccountException.class)
//    public ResponseData freeze(HttpServletRequest req, Exception e) {
//        return ResponseData.errorWithNotice("您的账户已经被冻结");
//    }
//
    /**
     * <p>
     * 您的账户已经被冻结
     * <p>
     *
     * @param e 异常类型
     * @return
     */
    @ExceptionHandler(value = IncorrectCredentialsException.class)
    public ResultData errorPsw(HttpServletRequest req, Exception e) {
        return ResultData.fail("密码错误");
    }
//
//    /**
//     * <p>
//     * 您错误的次数太多了吧,封你半小时
//     * <p>
//     *
//     * @param e 异常类型
//     * @return
//     */
//    @ExceptionHandler(value = ExcessiveAttemptsException.class)
//    public ResponseData errorTooMore(HttpServletRequest req, Exception e) {
//        return ResponseData.errorWithNotice("您错误的次数太多了吧,封你半小时");
//    }


    /**
     * <p>
     * 自定义 REST 业务异常
     * <p>
     *
     * @param e 异常类型
     * @return
     */
    @ExceptionHandler(value = ApiException.class)
    public ResultData handleBadApi(ApiException e) {
        return ResultData.fail(e.getMessage());
    }

    /**
     * 处理空指针的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ResultData exceptionHandler(NullPointerException e) {
        logger.error("发生空指针异常！原因是:", e);
        return ResultData.fail("空指针异常");
    }

//    /**
//     * 处理数据库重复的异常
//     *
//     * @param e
//     * @return
//     */
//    @ExceptionHandler(value = TooManyResultsException.class)
//    public ResponseData tooManyResultHandler(TooManyResultsException e) {
//        logger.error("发生数据库重复！原因是:", e);
//        return ResponseData.errorWithNotice("数据库重复");
//    }

    /**
     * 处理数据库唯一索引触发的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = DuplicateKeyException.class)
    public ResultData integrityReadHandler(DuplicateKeyException e) {
        logger.error("数据重复:", e);
        return ResultData.fail("数据重复请重新添加");
    }

    /**
     * SerializeUtils
     * 处理参数传空的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultData paramNullHandler(MethodArgumentNotValidException e) {
        logger.error("缺少必填参数值:", e);
        return ResultData.fail("缺少必填参数值:" + "/n" + e.getBindingResult().getFieldError().getField());
    }


    /**
     * SerializeUtils
     * 处理参数传空的异常（）
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResultData paramNullHandler(DataIntegrityViolationException e) {
        logger.error("违反数据完整性:", e);
        return ResultData.fail("违反数据完整性:"+"/n" + e.getCause().getMessage());
    }

    /**
     * 系统内部异常，打印异常栈
     */
    @ExceptionHandler(value = Exception.class)
    public ResultData handleBadRequest(Exception e) {
        logger.error("系统内部异常", e);
        return ResultData.fail("系统内部异常");
    }

}
