package io.github.mosiki.search;


import io.github.mosiki.app.document.Book;
import io.github.mosiki.app.mapper.ExtResultMapper;
import io.github.mosiki.app.query.BookQuery;
import io.github.mosiki.app.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 类名称：BookRepositoryTest
 * 类描述：演示SpringDataElasticSearch高亮查询
 * 创建人：WeJan
 * 创建时间：2018-09-02 10:38
 */
@Slf4j
@Component
public class HighlightBookRepositoryTest extends EsSearchApplicationTests {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Resource
    private ExtResultMapper extResultMapper;

    @Test
    public void testHighlightQuery() {
        BookQuery query = new BookQuery();
        query.setQueryString("穿越");

        // 复合查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        // 以下为查询条件, 使用 must query 进行查询组合
        MultiMatchQueryBuilder matchQuery = QueryBuilders.multiMatchQuery(query.getQueryString(), "name", "intro", "author");
        boolQuery.must(matchQuery);

        PageRequest pageRequest = PageRequest.of(query.getPage() - 1, query.getSize());

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withHighlightFields(
                        new HighlightBuilder.Field("name").preTags("<span style=\"color:red\">").postTags("</span>"),
                        new HighlightBuilder.Field("author").preTags("<span style=\"color:red\">").postTags("</span>"))
                .withPageable(pageRequest)
                .build();
        Page<Book> books = elasticsearchTemplate.queryForPage(searchQuery, Book.class, extResultMapper);

        books.forEach(e -> log.info("{}", e));
        // <span style="color:red">穿越</span>小道人
    }
}