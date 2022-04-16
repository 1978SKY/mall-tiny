package com.deep.search.service;

import com.deep.search.model.dto.SkuEsDTO;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.List;

/**
 * 商品上架
 *
 * @author Deep
 * @date 2022/3/21
 */
public interface UploadService {
    /**
     * 商品上架
     */
    boolean upload(@NonNull List<SkuEsDTO> skuEsDTOs) throws IOException;
}
