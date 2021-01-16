package com.tlg.bkwb.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tlg.bkwb.bean.Book;
import com.tlg.bkwb.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * @author tlg
 * @create 2021-01-06 17:38
 */
@Slf4j
@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    /**
     * 首页请求
     * @param p
     * @param model
     * @return
     */
    @RequestMapping({"/","/index","/index.html"})
    public String index(@RequestParam(value = "p",defaultValue = "1") Integer p ,Model model) {
        Page<Book> iPage = new Page<>(p,8);
        IPage<Book> page = bookService.page(iPage);

        model.addAttribute("page",page);
        return "index";
    }


    /**
     * 书籍详情页
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/book/{id}")
    public String getBook(@PathVariable Long id,Model model){
        Book book = bookService.getById(id);
        model.addAttribute("book",book);
        return "/book";
    }


}
