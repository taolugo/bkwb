package com.tlg.bkwb.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tlg.bkwb.bean.Order;
import com.tlg.bkwb.mapper.OrderMapper;
import com.tlg.bkwb.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * @author tlg
 * @create 2021-01-09 22:37
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
}
