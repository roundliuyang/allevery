package com.yly.spring.data.es.searchapi;


import com.yly.spring.data.es.config.Config;
import com.yly.spring.data.es.model.Article;
import com.yly.spring.data.es.model.Author;
import com.yly.spring.data.es.repository.ArticleRepository;
import jakarta.json.JsonObject;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class RestHighLevelClientTest {

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private ArticleRepository articleRepository;


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

//    @After
//    public void after() {
//        articleRepository.deleteAll();
//    }


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
    public void builder_test() throws Exception {
        SearchRequest searchRequest = new SearchRequest("blog");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("title","Tutorial"));

        searchSourceBuilder.query(boolQueryBuilder);

        // 设置分页参数
        int page = 0; 
        int pageSize = 10; 
        searchSourceBuilder.from(page * pageSize);
        searchSourceBuilder.size(pageSize);

        // 设置多字段排序，先按 post_date 升序排序，再按 id 排序
        searchSourceBuilder.sort("post_date", SortOrder.ASC);
//        searchSourceBuilder.sort("_id", SortOrder.ASC); // 使用 _id 进行次级排序
        searchRequest.source(searchSourceBuilder);
        
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        for(SearchHit hit : hits.getHits()){
            System.out.println(hit.getSourceAsString() + "---------------");
        }
    }

}
