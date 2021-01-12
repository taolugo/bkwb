package com.tlg.bkwb.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tlg.bkwb.bean.User;
import com.tlg.bkwb.mapper.UserMapper;
import com.tlg.bkwb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author tlg
 * @create 2021-01-07 2:34
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    // 注册的具体实现
    public synchronized boolean register(User user) {
        return userMapper.register(user)>0;
    }

    // 用户登录的具体实现
    public User login(String username, String password) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username).eq("password", password));

        return user;
    }

    // 查询用户名是否存在的具体实现
    public boolean exists(String username) {
        return userMapper.exists(username) > 0;
    }

    // 更新余额的具体实现
    public synchronized boolean updBalance(int userId, BigDecimal price) {
        return userMapper.updBalance(userId, price)>0;
    }

    @Override
    public Integer getIdByUsername(String username) {
        return userMapper.getIdByUsername(username);
    }

    public BigDecimal getBalance(Integer id){
        return userMapper.selectById(id).getBalance();
    }
}
