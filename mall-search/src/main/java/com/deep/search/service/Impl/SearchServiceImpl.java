package com.deep.search.service.Impl;

import com.alibaba.fastjson.JSON;
import com.deep.search.config.EsConfig;
import com.deep.search.exception.EsException;
import com.deep.search.model.constant.EsConstant;
import com.deep.search.model.dto.SkuEsDTO;
import com.deep.search.model.params.SearchParam;
import com.deep.search.model.vo.AttrVO;
import com.deep.search.model.vo.BrandVO;
import com.deep.search.model.vo.CatalogVO;
import com.deep.search.model.vo.SearchResultVO;
import com.deep.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 检索服务
 *
 * @author Deep
 * @date 2022/3/21
 */
@Slf4j
@Service("searchService")
public class SearchServiceImpl implements SearchService {

    @Autowired
    @Qualifier("esRestClient")
    private RestHighLevelClient client;

    @Override
    public SearchResultVO search(SearchParam searchParam) {
        SearchResultVO resultVO;
        // prepare the search request
        SearchRequest searchRequest = buildSearchRequest(searchParam);
        try {
            // get the search response
            SearchResponse searchResponse = client.search(searchRequest, EsConfig.COMMON_OPTIONS);
            // build the result
            resultVO = buildSearchResponse(searchParam, searchResponse);
        } catch (IOException e) {
            throw new EsException(e.getMessage());
        }
        return resultVO;
    }

    /**
     * 解析请求结果
     */
    private SearchResultVO buildSearchResponse(SearchParam searchParam, SearchResponse searchResponse) {
        SearchHits hits = searchResponse.getHits();
        SearchResultVO resultVO = new SearchResultVO();
        // 1、封装检索到的商品
        if (!ArrayUtils.isEmpty(hits.getHits())) {
            List<SkuEsDTO> products = Arrays.stream(hits.getHits()).map(hit -> {
                String jsonHit = hit.getSourceAsString();
                SkuEsDTO skuEsDTO = JSON.parseObject(jsonHit, SkuEsDTO.class);
                // set highlight
                if (StringUtils.hasLength(searchParam.getKeyword())) {
                    HighlightField skuTitle = hit.getHighlightFields().get("skuTitle");
                    skuEsDTO.setSkuTitle(skuTitle.getFragments()[0].string());
                }
                return skuEsDTO;
            }).collect(Collectors.toList());
            resultVO.setProducts(products);
        }
        // 2、分装分析出的品牌
        ParsedLongTerms brandAggs = searchResponse.getAggregations().get("brand_agg");
        List<BrandVO> brandVOS = brandAggs.getBuckets().parallelStream().map(brandAgg -> {
            long brandId = brandAgg.getKeyAsNumber().longValue();
            ParsedStringTerms brandNameAgg = brandAgg.getAggregations().get("brand_name_agg");
            String brandName = brandNameAgg.getBuckets().get(0).getKeyAsString();
            ParsedStringTerms brandImgAgg = brandAgg.getAggregations().get("brand_img_agg");
            String brandImg = brandImgAgg.getBuckets().get(0).getKeyAsString();
            return new BrandVO(brandId, brandName, brandImg);
        }).collect(Collectors.toList());
        resultVO.setBrands(brandVOS);
        // 3、封装分析出的分类
        ParsedLongTerms catAggs = searchResponse.getAggregations().get("catalog_agg");
        List<CatalogVO> catVOS = catAggs.getBuckets().parallelStream().map(catAgg -> {
            long catId = catAgg.getKeyAsNumber().longValue();
            ParsedStringTerms catalogNameAgg = catAgg.getAggregations().get("catalog_name_agg");
            String catName = catalogNameAgg.getBuckets().get(0).getKeyAsString();
            return new CatalogVO(catId, catName);
        }).collect(Collectors.toList());
        resultVO.setCatalogs(catVOS);
        // 4、封装分析出的属性
        ParsedNested attrAgg = searchResponse.getAggregations().get("attr_agg");
        ParsedLongTerms attrIdAggs = attrAgg.getAggregations().get("attr_id_agg");
        List<AttrVO> attrVOS = attrIdAggs.getBuckets().parallelStream().map(attrIdAgg -> {
            long attrId = attrIdAgg.getKeyAsNumber().longValue();
            ParsedStringTerms attrNameAgg = attrIdAgg.getAggregations().get("attr_name_agg");
            String attrName = attrNameAgg.getBuckets().get(0).getKeyAsString();
            ParsedStringTerms attrValueAgg = attrIdAgg.getAggregations().get("attr_value_agg");
            List<String> values = attrValueAgg.getBuckets().stream().map(MultiBucketsAggregation.Bucket::getKeyAsString).collect(Collectors.toList());
            return new AttrVO(attrId, attrName, values);
        }).collect(Collectors.toList());
        resultVO.setAttrs(attrVOS);
        // 5、封装分页信息
        long total = hits.getTotalHits().value;
        int totalPages = (int) total % EsConstant.PRODUCT_PAGE_SIZE == 0 ?
                (int) total / EsConstant.PRODUCT_PAGE_SIZE :
                ((int) total / EsConstant.PRODUCT_PAGE_SIZE + 1);
        resultVO.setTotal(total);
        resultVO.setPageNum(searchParam.getPageNum());
        resultVO.setTotalPages(totalPages);
        List<Integer> pageNaves = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            pageNaves.add(i);
        }
        resultVO.setPageNavs(pageNaves);

        return resultVO;
    }

    /**
     * 构建检索请求
     */
    private SearchRequest buildSearchRequest(SearchParam searchParam) {
        // 构建DSL对象
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 构造查询器
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // ------------------------------过滤条件start----------------------------------


        // 1.1 keyword【must有得分】
        if (StringUtils.hasLength(searchParam.getKeyword())) {
            // ①、skuTitle
            boolQueryBuilder.must(QueryBuilders.matchQuery("skuTitle", searchParam.getKeyword()));
        }

        // 1.2 分类【filter无得分】
        if (searchParam.getCatId() != null && searchParam.getCatId() > 0L) {
            boolQueryBuilder.filter(QueryBuilders.matchQuery("catalogId", searchParam.getCatId()));
        }

        // 1.3 品牌
        if (!CollectionUtils.isEmpty(searchParam.getBrandId())) {
            searchParam.getBrandId()
                    .parallelStream()
                    .forEach(item -> boolQueryBuilder.filter(QueryBuilders.matchQuery("brandId", item)));
        }

        // 1.4 属性
        if (!CollectionUtils.isEmpty(searchParam.getAttrs())) {
            searchParam.getAttrs().parallelStream().forEach(attr -> {
                BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
                String[] split = attr.split("_");
                boolQuery.must(QueryBuilders.termQuery("attrs.attrId", split[0]));
                boolQuery.must(QueryBuilders.termQuery("attrs.attrValue", split[1]));
                NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("attrs", boolQuery, ScoreMode.None);
                boolQueryBuilder.filter(nestedQuery);
            });
        }
        // 1.5 库存
        if (searchParam.getHasStock() != null) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("hasStock", searchParam.getHasStock() == 1));
        }
        // 1.6 价格区间
        sourceBuilder.query(boolQueryBuilder);
        // ------------------------------过滤条件end----------------------------------

        // -------------------------排序、分页、高亮start------------------------------
        // 2.1 排序
        if (StringUtils.hasLength(searchParam.getSort())) {
            String[] split = searchParam.getSort().split("_");
            SortOrder order = split[1].equalsIgnoreCase("asc") ? SortOrder.ASC : SortOrder.DESC;
            sourceBuilder.sort(split[0], order);
        }
        // 2.2 分页
        sourceBuilder.from((searchParam.getPageNum() - 1) * searchParam.getPageSize());
        sourceBuilder.size(searchParam.getPageSize());
        // 2.3 高亮
        if (StringUtils.hasLength(searchParam.getKeyword())) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("skuTitle");
            highlightBuilder.preTags("<b style='color:red'>");
            highlightBuilder.postTags("</b>");
            sourceBuilder.highlighter(highlightBuilder);
        }
        // -------------------------排序、分页、高亮end-----------------------------
        // ----------------------------聚合分析start------------------------------
        // 3.1 品牌聚合
        TermsAggregationBuilder brand_agg = AggregationBuilders.terms("brand_agg").field("brandId").size(10);
        brand_agg.subAggregation(AggregationBuilders.terms("brand_name_agg").field("brandName").size(1));
        brand_agg.subAggregation(AggregationBuilders.terms("brand_img_agg").field("brandImg").size(1));
        sourceBuilder.aggregation(brand_agg);
        // 3.2 分类聚合
        TermsAggregationBuilder cat_agg = AggregationBuilders.terms("catalog_agg").field("catalogId").size(20);
        cat_agg.subAggregation(AggregationBuilders.terms("catalog_name_agg").field("catalogName").size(1));
        sourceBuilder.aggregation(cat_agg);
        // 3.3 属性聚合【嵌入式】
        NestedAggregationBuilder attr_agg = AggregationBuilders.nested("attr_agg", "attrs");
        // 按照属性id聚合
        TermsAggregationBuilder attr_id_agg = AggregationBuilders.terms("attr_id_agg").field("attrs.attrId").size(10);
        // 再由属性id进行子聚合分析
        attr_id_agg.subAggregation(AggregationBuilders.terms("attr_name_agg").field("attrs.attrName").size(1));
        attr_id_agg.subAggregation(AggregationBuilders.terms("attr_value_agg").field("attrs.attrValue").size(50));
        // 嵌入式聚合
        attr_agg.subAggregation(attr_id_agg);
        sourceBuilder.aggregation(attr_agg);
        // ----------------------------聚合分析end------------------------------


//        log.info("DSL语句：{}", sourceBuilder);
        return new SearchRequest(new String[]{EsConstant.PRODUCT_INDEX}, sourceBuilder);
    }
}
