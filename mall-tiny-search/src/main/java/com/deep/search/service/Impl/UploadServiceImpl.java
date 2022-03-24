package com.deep.search.service.Impl;

import com.alibaba.fastjson.JSON;
import com.deep.search.config.EsConfig;
import com.deep.search.model.constant.EsConstant;
import com.deep.search.model.dto.SkuEsDTO;
import com.deep.search.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品上架
 *
 * @author Deep
 * @date 2022/3/21
 */
@Slf4j
@Service("uploadService")
public class UploadServiceImpl implements UploadService {

    @Autowired
    @Qualifier("esRestClient")
    private RestHighLevelClient client;

    @Override
    public boolean upload(List<SkuEsDTO> skuEsDTOs) throws IOException {
        Assert.notEmpty(skuEsDTOs, "skuEsDTOs不能为空!");
        // Request
        BulkRequest bulkRequest = new BulkRequest();
        skuEsDTOs.forEach(item -> {
            IndexRequest request = new IndexRequest(EsConstant.PRODUCT_INDEX);
            String json = JSON.toJSONString(item);
            request.source(json, XContentType.JSON);
            bulkRequest.add(request);
        });
        // Response
        BulkResponse bulkResponse = client.bulk(bulkRequest, EsConfig.COMMON_OPTIONS);
        if (bulkResponse.hasFailures()) {
            List<String> errors = Arrays.stream(bulkResponse.getItems())
                    .map(BulkItemResponse::getId).collect(Collectors.toList());
            log.info("商品上架错误：{}", errors);
            return false;
        }
        return true;
    }
}
