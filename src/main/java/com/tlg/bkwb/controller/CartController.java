package com.tlg.bkwb.controller;

import com.tlg.bkwb.bean.Book;
import com.tlg.bkwb.service.BookService;
import com.tlg.bkwb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author tlg
 * @create 2021-01-08 1:41
 */
@Slf4j
@Controller
public class CartController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;



    /**
     * 添加商品到购物车
     * @param username
     * @param bookId
     * @param count
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/cart/addToCart/{bookId}/{count}")
    public String addToCart(@PathVariable String bookId, @PathVariable Integer count, HttpSession session){
        String username = (String) session.getAttribute("loginUsername");
        if (count <= 0 || count > 999){
            return "数量不合法";
        }

        if (userService.exists(username)){
            BoundHashOperations hashOps = redisTemplate.boundHashOps(username);
//            if (hashOps.keys().isEmpty()){
                redisTemplate.boundHashOps(username).expire(24, TimeUnit.HOURS);
//            }

            if (hashOps.keys().contains(bookId)){
                Map map = (HashMap) hashOps.get(bookId);
                Integer oldCount = (Integer) map.get("count");
                map.put("count",oldCount + count);
                redisTemplate.boundHashOps(username).put(bookId,map);

            }else{
                Map<String,Object> map = new HashMap<>();
                Book book = bookService.getById(Integer.parseInt(bookId));
                map.put("book",book);
                map.put("count",count);
                redisTemplate.boundHashOps(username).put(bookId,map);
            }
            return "添加成功";
        }
        return "添加失败";
    }


    /**
     * 跳转至购物车页
     * @return
     */
    @RequestMapping("/cart/cart.html")
    public String cartPage(Model model,HttpSession session) {
        String loginUser = (String) session.getAttribute("loginUsername");
        Map map = redisTemplate.boundHashOps(loginUser).entries();
        model.addAttribute("cartMap",map);
        return "cart/cart";
    }

    /**
     * 获取购物车内容列表
     * @param session
     * @return
     */
    @ResponseBody
    @GetMapping("/cart/getCartList")
    public Map<String, Object> getCartList(HttpSession session) {
        String loginUser = (String) session.getAttribute("loginUsername");
        Map map = redisTemplate.boundHashOps(loginUser).entries();
        log.info("============" + map.toString());
        return map;
    }


    /**
     * 购物车移除item
     * @param bookId
     * @param session
     * @return
     */
    @PostMapping("/cart/delItem/{bookId}")
    public String deleteItemOnCart(@PathVariable String bookId, HttpSession session){
        String loginUser = (String) session.getAttribute("loginUsername");
        if (!redisTemplate.boundHashOps(loginUser).keys().contains(bookId)) {
            return "/error/5xx.html";
        }
        redisTemplate.boundHashOps(loginUser).delete(bookId);
        return "redirect:/cart/cart.html";
    }

    /**
     * 清空购物车
     * @param session
     * @return
     */
    @ResponseBody
    @GetMapping("/cart/clearCart")
    public String clearCart(HttpSession session){
        String loginUser = (String) session.getAttribute("loginUsername");


        redisTemplate.delete(loginUser);
        return "/index.html";
    }


    /**
     * 修改购物车item数量
     * @param bookId
     * @param count
     * @param session
     * @return
     */
    @PostMapping("/cart/updCount/{bookId}/{count}")
    public String updCount(@PathVariable String bookId, @PathVariable Integer count,HttpSession session){
        String loginUser = (String) session.getAttribute("loginUsername");

        if (count <= 0 || count > 999 || !redisTemplate.boundHashOps(loginUser).keys().contains(bookId)){

            return "/error/5xx.html";
        }
        BoundHashOperations hashOps = redisTemplate.boundHashOps(loginUser);
        Map map = (HashMap) hashOps.get(bookId);
        map.put("count",count);
        redisTemplate.boundHashOps(loginUser).put(bookId,map);
        return "redirect:/cart/cart.html";

    }



}
