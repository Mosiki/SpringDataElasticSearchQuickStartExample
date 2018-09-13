package io.github.mosiki.app.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * 类名称：Book
 * 类描述：
 * 创建人：WeJan
 * 创建时间：2018-09-02 09:51
 *
 * indexName ： 索引名称
 * type: 索引类型
 * createIndex: 最好关闭，否则启动项目会自动建立索引，索引最好手动建
 */
@Data
@Document(indexName = "novel", type = "book", createIndex = false)
public class Book implements Serializable {

    private static final long serialVersionUID = 8504604495927552402L;

    /**
     * 需要添加 @Id 标识主键
     */
    @Id
    private Integer id;

    private Integer words;

    private String intro;

    private String name;

    private Integer sort;

    private Boolean vip;

    private Integer site;

    private String author;

    private Integer collection;

    private Integer click;

    private Integer popularity;

    private Integer goods;

    private Integer status;

    /**
     * 需要自定义时间格式化格式，否则会使用默认时间格式化
     */
    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss")
    private Date updatetime;

}
