package com.deep.ware.controller;

import com.deep.common.utils.PageUtils;
import com.deep.common.utils.R;
import com.deep.ware.service.WareSkuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 商品库存
 *
 * @author Deep
 * @date 2022/3/28
 */
@RestController
@RequestMapping("/api/ware/waresku")
public class WareSkuController {
    @Autowired
    private WareSkuService wareSkuService;

    @RequestMapping("/list")
    @ApiOperation("商品库存列表")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = wareSkuService.queryPage(params);

        return R.ok().put("page", page);
    }
}
