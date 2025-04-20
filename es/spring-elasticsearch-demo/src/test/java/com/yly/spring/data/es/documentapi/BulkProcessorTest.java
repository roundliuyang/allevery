package com.yly.spring.data.es.documentapi;

import com.yly.spring.data.es.config.Config;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class BulkProcessorTest {

    @Autowired
    private RestHighLevelClient client;

    @Test
    public void bulk_processor_test() throws Exception {
        BulkProcessor.Listener listener = new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                System.out.println("bulk processor before" + "---------------------------");
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                System.out.println("bulk processor before" + "---------------------------");
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                System.out.println("bulk processor before" + "---------------------------");
            }
        };
        BulkProcessor bulkProcessor = BulkProcessor
                // request：表示批量处理请求，bulkListener: 表示监听批量请求执行的回调.
                // client.bulkAsync: 使用 client 的 bulkAsync 方法异步地执行批量请求
                .builder((request, bulkListener) -> client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener), listener)
                // 设置批量操作的最大操作数量。在此配置中，每当积累到2个操作时，批量请求会自动触发发送
                .setBulkActions(2)
                // 批量操作的最大字节大小。
                .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
                // 批量请求的自动刷新间隔时间
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                // 并发请求的数量
                .setConcurrentRequests(1)
                // 设置重试策略，在请求失败时进行重试.exponentialBackoff: 使用指数退避策略进行重试.TimeValue.timeValueMillis(100): 初始等待时间为100毫秒.
                .setBackoffPolicy(
                        BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();

        // UpdateRequest
        UpdateRequest request = new UpdateRequest("blog", "M_QHQrrZEBdDWX6__Cs4u8");
        String jsonString = "{\"title\":\"insert2422\",\"authors\":[{\"name\":\"insert222\"}]}";
        request.doc(jsonString, XContentType.JSON).upsert(jsonString, XContentType.JSON);

        BulkProcessor add = bulkProcessor.add(request);
        System.out.println("Request added to bulk processor");

        // Close bulkProcessor and wait for the operations to complete. very important
        bulkProcessor.awaitClose(10, TimeUnit.SECONDS);
        System.out.println("Bulk processor closed");
    }
}
