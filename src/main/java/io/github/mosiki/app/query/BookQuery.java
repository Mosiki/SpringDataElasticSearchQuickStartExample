package io.github.mosiki.app.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * 类名称：BookQuery
 * 类描述：封装Book 查询参数
 * 创建人：WeJan
 * 创建时间：2018年09月04日 13:30
 */
@Data
public class BookQuery {

    private String queryString;

    private Integer page = 1;

    private Integer size = 20;

    private Integer wordsBegin;

    private Integer wordsEnd;

    private Integer sort;

    private Boolean vip;

    private Integer site;

    private Integer collection;

    private Integer click;

    private Integer popularity;

    private Integer goods;

    private Integer status;

    private Date updatetime;


}
