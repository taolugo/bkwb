package com.tlg.bkwb.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tlg.bkwb.bean.Book;
import com.tlg.bkwb.exception.MyException;
import com.tlg.bkwb.mapper.BookMapper;
import com.tlg.bkwb.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author tlg
 * @create 2021-01-07 16:32
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper,Book> implements BookService {

    @Autowired
    private BookMapper bookMapper;


    @Transactional(rollbackFor = Exception.class,isolation=Isolation.READ_COMMITTED)
    public synchronized boolean updateCountQuery(List<Map> mapList) throws MyException{
        for (Map map : mapList) {
            Long bookId = (Long) map.get("bookId");
            Integer count = (Integer) map.get("count");
            Integer stock = bookMapper.getBookStockById(bookId);

            if (stock < count){
                throw new MyException("库存不够了！ 商品为：" + map.get("bookName"));
            }
            bookMapper.updStock(bookId,count);
        }
        return true;
    }
}
