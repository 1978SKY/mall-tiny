package com.deep.product.controller.admin;

import com.deep.common.utils.PageUtils;
import com.deep.common.utils.R;
import com.deep.product.model.params.SpuSaveParam;
import com.deep.product.service.SpuInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * spu信息
 *
 * @author Deep
 * @date 2022/3/20
 */
@Api(value = "spu信息", tags = "spuInfoController")
@RestController
@RequestMapping("/api/product/spuinfo")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;

    @RequestMapping("/list")
    @ApiOperation("获取spu页面")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = spuInfoService.queryPage(params);

        return R.ok().put("page", page);
    }

    @PostMapping("/save")
    @ApiOperation("新增spu&sku")
    public R save(@RequestBody SpuSaveParam spuSaveParam) {
        spuInfoService.saveSpuDetail(spuSaveParam);

        return R.ok();
    }


}
