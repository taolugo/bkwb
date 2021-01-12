package com.tlg.bkwb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tlg.bkwb.bean.User;


import java.math.BigDecimal;

/**
 * @author tlg
 * @create 2021-01-07 2:28
 */

public interface UserService extends IService<User> {
    // 用户注册
    boolean register(User user);

    // 用户登录
    User login(String username, String password);

    // 注册时，查询用户名是否被占用
    boolean exists(String username);

    // 更新余额
    boolean updBalance(int userId, BigDecimal price);

    Integer getIdByUsername(String username);

    //查询余额
    BigDecimal getBalance(Integer id);
}
