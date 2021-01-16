package com.tlg.bkwb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tlg.bkwb.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

/**
 * @author tlg
 * @create 2021-01-07 2:40
 */

public interface UserMapper extends BaseMapper<User> {


    // 用户注册
    @Insert("insert into t_user (username,password,nickname) values (#{username},#{password},#{nickname})")
    @Options(useGeneratedKeys = true,keyProperty = "userId")
    Integer register(User user);


    // 注册时，检查用户名被占用
    @Select("select count(0) from t_user where username=#{username}")
    int exists(String username);

    // 更新余额
    @Update("update t_user set balance=balance-${price} where user_id=${userId} and balance-${price}>0")
    int updBalance(Long userId, BigDecimal price);


    @Select("Select u_id from t_user where username=#{username}")
    Integer getIdByUsername(String username);
}
