package com.deep.search.controller.admin;

import com.deep.common.utils.R;
import com.deep.search.model.dto.SkuEsDTO;
import com.deep.search.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * 商品上架
 *
 * @author Deep
 * @date 2022/3/21
 */
@Slf4j
@Api(value = "商品上架")
@RestController
@RequestMapping("api/search/save")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/product")
    @ApiOperation("上架商品")
    public R productStatusUp(@RequestBody List<SkuEsDTO> skuEsDTOs) {
        boolean status;
        try {
            status = uploadService.upload(skuEsDTOs);
        } catch (IOException e) {
            log.error("商品上架错误：{}", e);
            return R.error(500, "商品上架错误");
        }
        return status ? R.ok() : R.error(500, "商品上架错误");
    }
}
