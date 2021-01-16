package com.tlg.bkwb.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tlg
 * @create 2021-01-09 21:02
 */
@TableName("t_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
public class Order {
    @TableId
    private String orderId; //订单编号
    private Long userId; //订单归属
    private String detail; //订单详情
    private Date createDate; //订单创建日期
    private Date paymentDate; //订单支付日期
    private BigDecimal amount; //总价
    private Integer state; //订单状态 0未支付 1已支付 2已失效

}
