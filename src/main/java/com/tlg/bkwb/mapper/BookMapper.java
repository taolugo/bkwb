package com.tlg.bkwb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tlg.bkwb.bean.Book;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author tlg
 * @create 2021-01-06 22:35
 */
//使用Mybatis-plus增强接口
public interface BookMapper extends BaseMapper<Book> {
    //获取库存
    @Select("Select stock from t_book where book_id=#{bookId}")
    Integer getBookStockById(Integer bookId);

    //修改库存
    @Update("update t_book set stock=stock-#{count} where book_id=#{bookId}")
    Integer updStock(Integer bookId, Integer count);
}
