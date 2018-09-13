package io.github.mosiki.app.repository;

import io.github.mosiki.app.document.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;



/**
 * 类名称：BookRepository
 * 类描述：
 * 创建人：WeJan
 * 创建时间：2018-09-02 10:27
 */
public interface BookRepository extends ElasticsearchRepository<Book, Integer> {

}
