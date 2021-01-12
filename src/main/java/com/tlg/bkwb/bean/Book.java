package com.tlg.bkwb.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Objects;

/**
 * @author tlg
 * @create 2021-01-06 18:04
 */
@TableName("t_book")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
public class Book implements Serializable {
    @TableId
    private Integer bookId;
    private String bookName; //书名
    private String author; //作者
    private Double price; //价格
    private Integer page; //价格
    private String press; //出版社
    private Date date; //出版日期
    private String summary; //简介
    private String image; //图片
    private Integer stock; //库存
    private Integer states; //状态 1在售


}
