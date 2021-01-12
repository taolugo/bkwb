package com.tlg.bkwb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tlg.bkwb.bean.Book;

import java.util.List;
import java.util.Map;

/**
 * @author tlg
 * @create 2021-01-07 16:31
 */

public interface BookService extends IService<Book> {

    //批量更新库存
    boolean updateCountQuery(List<Map> mapList) throws Exception;
}
