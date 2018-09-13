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
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 类名称：BookRepositoryTest
 * 类描述：演示SpringDataElasticSearch基本查询
 * 创建人：WeJan
 * 创建时间：2018-09-02 10:38
 */
@Slf4j
@Component
public class BookRepositoryTest extends EsSearchApplicationTests{

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findBook() {

        BookQuery query = new BookQuery();
        query.setQueryString("魔");
        query.setSite(2);// 1 是男生 2 是女生
        query.setSort(29); // 29 是玄幻
        query.setVip(true);// 查询 vip 作品
        query.setWordsBegin(0); // 查询字数在 0-25w 之间的作品
        query.setWordsEnd(500000);
        query.setPage(1);// 分页页码
        query.setSize(10);// 每页显示数

        // 复合查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        // 以下为查询条件, 使用 must query 进行查询组合
        MultiMatchQueryBuilder matchQuery = QueryBuilders.multiMatchQuery(query.getQueryString(), "name", "intro", "author");
        boolQuery.must(matchQuery);

        // 以下为过滤筛选条件，使用 filter 比使用 must query 性能要好
        TermQueryBuilder siteQuery = QueryBuilders.termQuery("site", query.getSite());
        boolQuery.filter(siteQuery);
        TermQueryBuilder sortQuery = QueryBuilders.termQuery("sort", query.getSort());
        boolQuery.filter(sortQuery);
        TermQueryBuilder vipQuery = QueryBuilders.termQuery("vip", query.getVip());
        boolQuery.filter(vipQuery);
        RangeQueryBuilder wordsQuery = QueryBuilders.rangeQuery("words").gt(query.getWordsBegin()).lt(query.getWordsEnd());
        boolQuery.filter(wordsQuery);

        Sort sort = Sort.by(Sort.Direction.DESC, "click");
        // 分页 同时根据 点击数 click 进行降序排列
        PageRequest pageRequest = PageRequest.of(query.getPage() - 1, query.getSize(), sort);

//        log.info("{}", boolQuery); // 打印出查询 json
        bookRepository.search(boolQuery, pageRequest)
                .forEach(e -> log.info("作品信息：{}", e));
    }
}