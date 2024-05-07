package com.yly.spring.data.es;


import com.yly.spring.data.es.config.Config;
import com.yly.spring.data.es.model.Article;
import com.yly.spring.data.es.model.Author;
import com.yly.spring.data.es.repository.ArticleRepository;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQuery;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.junit.Assert.assertEquals;

/**
 * This Manual test requires: Elasticsearch instance running on localhost:9200.
 * <p>
 * The following docker command can be used: docker run -d --name es762 -p
 * 9200:9200 -e "discovery.type=single-node" elasticsearch:7.6.2
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class ElasticSearchQueryManualTest {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private RestHighLevelClient client;

    private final Author johnSmith = new Author("John Smith");
    private final Author johnDoe = new Author("John Doe");

    @Before
    public void before() {
        Article article = new Article("Spring Data Elasticsearch");
        article.setAuthors(asList(johnSmith, johnDoe));
        article.setTags("elasticsearch", "spring data");
        articleRepository.save(article);

        article = new Article("Search engines");
        article.setAuthors(asList(johnDoe));
        article.setTags("search engines", "tutorial");
        articleRepository.save(article);

        article = new Article("Second Article About Elasticsearch");
        article.setAuthors(asList(johnSmith));
        article.setTags("elasticsearch", "spring data");
        articleRepository.save(article);

        article = new Article("Elasticsearch Tutorial");
        article.setAuthors(asList(johnDoe));
        article.setTags("elasticsearch");
        articleRepository.save(article);
    }

    @After
    public void after() {
        articleRepository.deleteAll();
    }

    @Test
    public void givenFullTitle_whenRunMatchQuery_thenDocIsFound() {
        final NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("title", "Search engines").operator(Operator.AND))
                .build();
        final SearchHits<Article> articles = elasticsearchOperations.search(searchQuery, Article.class, IndexCoordinates.of("blog"));
        assertEquals(1, articles.getTotalHits());
    }

    @Test
    public void givenOneTermFromTitle_whenRunMatchQuery_thenDocIsFound() {
        final NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("title", "Engines Solutions"))
                .build();

        final SearchHits<Article> articles = elasticsearchOperations.search(searchQuery, Article.class, IndexCoordinates.of("blog"));

        assertEquals(1, articles.getTotalHits());
        assertEquals("Search engines", articles.getSearchHit(0)
                .getContent()
                .getTitle());
    }

    @Test
    public void givenPartTitle_whenRunMatchQuery_thenDocIsFound() {
        final NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("title", "elasticsearch data"))
                .build();

        final SearchHits<Article> articles = elasticsearchOperations.search(searchQuery, Article.class, IndexCoordinates.of("blog"));

        assertEquals(3, articles.getTotalHits());
    }

    @Test
    public void givenFullTitle_whenRunMatchQueryOnVerbatimField_thenDocIsFound() {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("title.verbatim", "Second Article About Elasticsearch"))
                .build();

        SearchHits<Article> articles = elasticsearchOperations.search(searchQuery, Article.class, IndexCoordinates.of("blog"));

        assertEquals(1, articles.getTotalHits());

        searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("title.verbatim", "Second Article About"))
                .build();

        articles = elasticsearchOperations.search(searchQuery, Article.class, IndexCoordinates.of("blog"));
        assertEquals(0, articles.getTotalHits());
    }

    @Test
    public void givenNestedObject_whenQueryByAuthorsName_thenFoundArticlesByThatAuthor() {
        final QueryBuilder builder = nestedQuery("authors", boolQuery().must(termQuery("authors.name", "smith")), ScoreMode.None);

        final NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(builder)
                .build();
        final SearchHits<Article> articles = elasticsearchOperations.search(searchQuery, Article.class, IndexCoordinates.of("blog"));

        assertEquals(2, articles.getTotalHits());
    }

    @Test
    public void givenAnalyzedQuery_whenMakeAggregationOnTermCount_thenEachTokenCountsSeparately() throws Exception { 
        // 创建了一个名为"top_tags"的术语聚合（Terms Aggregation），该聚合将应用于"title"字段。这意味着它将根据"title"字段中的术语（terms）对文档进行分组
        final TermsAggregationBuilder aggregation = AggregationBuilders.terms("top_tags")
                .field("title");

        // 创建一个用于构建搜索请求的SearchSourceBuilder对象，并将上面创建的聚合添加到其中
        final SearchSourceBuilder builder = new SearchSourceBuilder().aggregation(aggregation);
        // 创建了一个搜索请求对象，并指定了索引名称为"blog"，并将之前构建的SearchSourceBuilder设置为请求的源
        final SearchRequest searchRequest = new SearchRequest("blog").source(builder);

        // 执行了搜索请求，并通过Elasticsearch的客户端（可能是通过REST API或者其他方式）发送请求，并将响应保存在response对象中
        final SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        // 从搜索响应中获取聚合结果，并将其存储在一个Map中，其中键是聚合的名称，值是Aggregation对象
        final Map<String, Aggregation> results = response.getAggregations()
                .asMap();
        
        // 从上一步得到的Map中获取名为"top_tags"的聚合结果，并将其强制转换为ParsedStringTerms类型，这是Elasticsearch中用于表示字符串类型的术语聚合结果的类
        final ParsedStringTerms topTags = (ParsedStringTerms) results.get("top_tags");

        final List<String> keys = topTags.getBuckets()
                .stream()
                .map(MultiBucketsAggregation.Bucket::getKeyAsString)
                .sorted()
                .collect(toList());
        assertEquals(asList("about", "article", "data", "elasticsearch", "engines", "search", "second", "spring", "tutorial"), keys);
    }

    @Test
    public void givenNotAnalyzedQuery_whenMakeAggregationOnTermCount_thenEachTermCountsIndividually() throws Exception {
        final TermsAggregationBuilder aggregation = AggregationBuilders.terms("top_tags")
                .field("tags")
                .order(BucketOrder.count(false));

        final SearchSourceBuilder builder = new SearchSourceBuilder().aggregation(aggregation);
        final SearchRequest searchRequest = new SearchRequest().indices("blog")
                .source(builder);

        final SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        final Map<String, Aggregation> results = response.getAggregations()
                .asMap();
        final ParsedStringTerms topTags = (ParsedStringTerms) results.get("top_tags");

        final List<String> keys = topTags.getBuckets()
                .stream()
                .map(MultiBucketsAggregation.Bucket::getKeyAsString)
                .collect(toList());
        assertEquals(asList("elasticsearch", "spring data", "search engines", "tutorial"), keys);
    }

    @Test
    public void givenNotExactPhrase_whenUseSlop_thenQueryMatches() {
        final NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchPhraseQuery("title", "spring elasticsearch").slop(1))
                .build();

        final SearchHits<Article> articles = elasticsearchOperations.search(searchQuery, Article.class, IndexCoordinates.of("blog"));

        assertEquals(1, articles.getTotalHits());
    }

    @Test
    public void givenPhraseWithType_whenUseFuzziness_thenQueryMatches() {
        final NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("title", "spring date elasticserch").operator(Operator.AND)
                .fuzziness(Fuzziness.ONE)
                .prefixLength(3))
                .build();

        final SearchHits<Article> articles = elasticsearchOperations.search(searchQuery, Article.class, IndexCoordinates.of("blog"));

        assertEquals(1, articles.getTotalHits());
    }

    @Test
    public void givenMultimatchQuery_whenDoSearch_thenAllProvidedFieldsMatch() {
        final NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(multiMatchQuery("tutorial").field("title")
                .field("tags")
                .type(MultiMatchQueryBuilder.Type.BEST_FIELDS))
                .build();

        final SearchHits<Article> articles = elasticsearchOperations.search(searchQuery, Article.class, IndexCoordinates.of("blog"));

        assertEquals(2, articles.getTotalHits());
    }

    @Test
    public void givenBoolQuery_whenQueryByAuthorsName_thenFoundArticlesByThatAuthorAndFilteredTag() {
        final QueryBuilder builder = boolQuery().must(nestedQuery("authors", boolQuery().must(termQuery("authors.name", "doe")), ScoreMode.None))
                .filter(termQuery("tags", "elasticsearch"));

        final NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(builder)
                .build();
        final SearchHits<Article> articles = elasticsearchOperations.search(searchQuery, Article.class, IndexCoordinates.of("blog"));

        assertEquals(2, articles.getTotalHits());
    }
}
