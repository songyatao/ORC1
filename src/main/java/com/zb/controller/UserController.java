package com.zb.controller;


import com.zb.Result.Result;
import com.zb.Result.ResultBuilder;
import com.zb.entity.User;
import com.zb.service.UserService;
import com.zb.tools.HttpResponse;
import com.zb.tools.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@RestController
@RequestMapping("/user")
@CrossOrigin //解决跨域问题
@Slf4j
public class UserController {
    //注入userService
    @Autowired
    private UserService userService;

    /**
     * 用户注册接口
     *
     * @param user 包含用户名和密码的用户对象，通过RequestBody接收前端传来的JSON数据
     * @return 返回注册结果，如果用户名不存在，则返回注册成功结果；否则返回错误信息
     * 对参数没有进行验证，仅用于演示
     */
    @PostMapping("/register")//ok
    public HttpResponse register(@RequestBody User user) {
        String uname = user.getUsername();
        String psw = user.getPassword();
        User byuser = userService.findByUser(uname);
        //查询数据库判断该用户是否存在
        if (byuser == null) {
            //不存在则注册成功，将用户名和密码添加库中
            userService.register(uname, psw);
            return ResultBuilder.successNoData(ResultCode.REGISTER_SUCCESS);
        }
        return ResultBuilder.faile(ResultCode.USER_HAS_EXISTED);
    }

    /**
     * 用户登录接口
     *
     * @param user 包含用户名和密码的用户对象，通过RequestBody接收前端传来的JSON数据
     * @return 返回登录结果，如果用户名正确且密码匹配，则返回登录成功结果；否则返回错误信息
     * 此登陆接口没有涉及对密码的加密，因此仅用于演示，在实际应用中应使用加密技术保护密码
     * 也没有设计token，因此登录成功后，用户无法退出，需要手动关闭浏览器或重新打开浏览器
     */
    @PostMapping("/login")//ok
    public HttpResponse log(@RequestBody User user) {
        // 提取用户名和密码
        String uname = user.getUsername();
        String pword = user.getPassword();
        // 根据用户名查找用户
        User byUser = userService.findByUser(uname);
        if (byUser == null) {
            // 如果用户名不存在，则返回用户名错误信息
            return ResultBuilder.faile(ResultCode.USER_LOGIN_ERROR);
        }
        if (pword.equals(byUser.getPassword())) {
            // // 如果密码匹配，则登录成功
            return ResultBuilder.successNoData(ResultCode.LOGIN_SUCCESS);
        }
        // 如果密码不匹配，则返回密码错误信息
        return ResultBuilder.faile(ResultCode.USER_LOGIN_ERROR);
    }

}