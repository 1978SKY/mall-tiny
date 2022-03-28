package com.deep.search.config;

import com.deep.search.config.properties.ESProperties;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Elastic Search配置类
 *
 * @author Deep
 * @date 2022/3/22
 */
@Configuration
@EnableConfigurationProperties(ESProperties.class)
public class EsConfig {

    /**
     * 请求设置
     */
    public static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        // 安全验证
//		builder.addHeader("Authorization", "Bearer " + TOKEN);
//		builder.setHttpAsyncResponseConsumerFactory(
//				new HttpAsyncResponseConsumerFactory
//						.HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 * 1024));
        COMMON_OPTIONS = builder.build();
    }

    /**
     * ES客户端创建
     */
    @Bean(name = "esRestClient")
    public RestHighLevelClient esRestClient(ESProperties properties) {
        return new RestHighLevelClient(
                RestClient.builder(new HttpHost(properties.getHost(), properties.getPort(), "http"))
        );
    }
}
