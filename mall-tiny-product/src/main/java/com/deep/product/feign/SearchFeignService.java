package com.deep.product.feign;

import com.deep.common.utils.R;
import com.deep.product.model.dto.SkuEsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 检索服务远程服务
 *
 * @author Deep
 * @date 2022/3/27
 */
@FeignClient("mall-tiny-search")
public interface SearchFeignService {
    /**
     * 商品上架
     */
    @PostMapping("api/search/save/product")
    R uploadProduct(@RequestBody List<SkuEsDTO> skuEsDTOs);
}
