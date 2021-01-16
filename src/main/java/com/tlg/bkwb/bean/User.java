package com.tlg.bkwb.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;


/**
 * @author tlg
 * @create 2021-01-07 2:28
 */
@TableName("t_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class User {
    @TableId(value = "user_id",type = IdType.AUTO)
    private Long userId;
    private String nickname;
    private String username;
    private String password;
    private BigDecimal balance;  //余额
    private Integer root;        //权限
    private Integer state;       //状态

}
