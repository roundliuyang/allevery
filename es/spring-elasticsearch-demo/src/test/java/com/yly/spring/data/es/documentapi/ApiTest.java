package com.yly.spring.data.es.documentapi;


import com.yly.spring.data.es.config.Config;
import com.yly.spring.data.es.model.Article;
import com.yly.spring.data.es.model.Author;
import com.yly.spring.data.es.repository.ArticleRepository;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class ApiTest {

    @Autowired
    private RestHighLevelClient client;


    /**
     * Updates with a partial document
     */
    @Test
    public void update_test() throws Exception {
        UpdateRequest request = new UpdateRequest("blog", "M_QHQZEBdDWX6__Cs7u0");
        String jsonString = "{\"title\":\"yuanliuyang2\",\"authors\":[{\"name\":\"qingrenjie2\"}]}";
        request.doc(jsonString, XContentType.JSON);

        try {
            UpdateResponse updateResponse = client.update(
                    request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {
            }
        }

    }

    /**
     * Upserts
     * If the document does not already exist, it is possible to define some content that will be inserted as a new document using the upsert method:
     */
    @Test
    public void upsert_test() throws Exception {
        UpdateRequest request = new UpdateRequest("blog", "M_QHQZEBdDWX6__Cs7u7");
        String jsonString = "{\"title\":\"insert222\",\"authors\":[{\"name\":\"insert222\"}]}";
        request.doc(jsonString, XContentType.JSON).upsert(jsonString, XContentType.JSON);

        try {
            UpdateResponse updateResponse = client.update(
                    request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {
            }
        }

    }
}
