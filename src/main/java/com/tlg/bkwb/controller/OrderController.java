package com.tlg.bkwb.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tlg.bkwb.bean.Book;
import com.tlg.bkwb.bean.Order;
import com.tlg.bkwb.exception.MyException;
import com.tlg.bkwb.service.BookService;
import com.tlg.bkwb.service.OrderService;
import com.tlg.bkwb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author tlg
 * @create 2021-01-09 22:39
 */
@Slf4j
@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 订单页
     * @param session
     * @param model
     * @return
     */
    @RequestMapping({"/order/","/order/order.html"})
    public String orderPage(HttpSession session, Model model){
        Long userId = (Long) session.getAttribute("loginId");
        List<Order> list = orderService.list(new QueryWrapper<Order>().eq("user_id",userId).orderByDesc("create_date"));
        model.addAttribute("orderList",list);
        System.out.println("=============" + list);
        return "order/order";
    }

    /**
     * 创建订单
     * @param session
     * @param model
     * @return
     */
    @Transactional(isolation= Isolation.READ_COMMITTED)
    @PostMapping("/cart/createOrder")
    public String createOrder(HttpSession session, Model model){

        String loginUser = (String) session.getAttribute("loginUsername");

        System.out.println("count=====" + redisTemplate.boundHashOps(loginUser).size());
        if (!redisTemplate.hasKey(loginUser) || redisTemplate.boundHashOps(loginUser).size() == 0){
            model.addAttribute("error","购物车为空！！！");
            return "/error/5xx.html";
        }

        BoundHashOperations hashOps = redisTemplate.boundHashOps(loginUser);
        List<Map> values = hashOps.values();
        List<Map> mapList = new ArrayList<>();
        BigDecimal amount = new BigDecimal(0);
        for (int i = 0; i < values.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            Book book = (Book) values.get(i).get("book");
            Integer count = (Integer) values.get(i).get("count");
            if (count <= 0){
                return "/error/5xx.html";
            }
            map.put("bookId",book.getBookId());
            map.put("bookName",book.getBookName());
            map.put("bookPrice",book.getPrice());
            map.put("count", count);
            BigDecimal price = book.getPrice().multiply(BigDecimal.valueOf(count));
            amount = amount.add(price);
            mapList.add(map);
        }



        try {
            bookService.updateCountQuery(mapList);
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            e.printStackTrace();
            return "/error/5xx.html";
        }

        Long userId = (Long) session.getAttribute("loginId");
        String detail = JSON.toJSONString(mapList);
        Order order = new Order();
        String id = UUID.randomUUID().toString().replaceAll("-","");
        order.setOrderId(id);
        order.setUserId(userId);
        order.setDetail(detail);
        order.setAmount(amount);
        order.setCreateDate(new Date(System.currentTimeMillis()));
        order.setState(0);
        boolean result = orderService.save(order);
        redisTemplate.delete(loginUser);

        log.info("=========订单创建：" + result + "  订单号：" + order.getOrderId());

        return "redirect:/order/" + id;

    }

    /**
     * 订单详情页
     * @param orderId
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/order/{orderId}")
    public String orderDetail(@PathVariable String orderId,HttpSession session, Model model){
        Long userId = (Long) session.getAttribute("loginId");
        Order order = orderService.getById(orderId);
        if (order.getUserId() == userId){
            model.addAttribute("order",order);
            return "/order/detail";
        }
        return "/error/5xx.html";
    }

    /**
     * 订单支付
     * @param orderId
     * @param session
     * @param model
     * @return
     */
    @Transactional(isolation= Isolation.READ_COMMITTED)
    @PostMapping("/order/pay/{orderId}")
    public String payOrder(@PathVariable String orderId,HttpSession session,Model model){
        Long userId = (Long) session.getAttribute("loginId");
        Order order = orderService.getById(orderId);

        System.out.println("in");
        if (order.getState() == 0 && userId == order.getUserId()){
            BigDecimal balance = userService.getBalance(userId);
            System.out.println("balance = " + balance);
            if (order.getAmount().compareTo(balance) <= 0){
                System.out.println("gg");
                boolean result = userService.updBalance(userId, order.getAmount());
                if (result == false){
                    throw new MyException("余额修改异常");
                }
                order.setState(1);
                order.setPaymentDate(new Date(System.currentTimeMillis()));
                orderService.updateById(order);
                log.info("订单" + order.getOrderId() + "完成");
                return "redirect:/order/order.html";
            }else{
                System.out.println("账户余额不足");
                model.addAttribute("error","账户余额不足");
                return "/error/5xx.html";
            }

        }
        System.out.println("error");
        return "redirect:/order/" + orderId;
    }
}
