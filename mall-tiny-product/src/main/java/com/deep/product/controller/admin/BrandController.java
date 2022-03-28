package com.deep.product.controller.admin;

import com.deep.common.utils.PageUtils;
import com.deep.common.utils.R;
import com.deep.product.model.entity.BrandEntity;
import com.deep.product.model.params.BrandParam;
import com.deep.product.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 品牌管理
 *
 * @author Deep
 * @date 2022/3/15
 */
@Api(value = "品牌管理")
@RestController
@RequestMapping("/api/product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("/list")
    @ApiOperation("获取所有品牌")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }

    @PostMapping("/update/status")
    @ApiOperation("修改状态")
    public R updateStatus(@RequestBody BrandParam brand) {
        brandService.updateStatus(brand.getBrandId(), brand.getShowStatus());

        return R.ok();
    }

    @PostMapping("/update")
    @ApiOperation("更新品牌")
    public R update(@RequestBody BrandParam brand) {
        BrandEntity entity = brand.convertTo();
        brandService.update(entity);

        return R.ok();
    }

    @PostMapping("/save")
    @ApiOperation("新增品牌")
    public R save(@RequestBody BrandParam brand) {
        BrandEntity entity = brand.convertTo();
        brandService.save(entity);

        return R.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("删除品牌")
    public R delete(@RequestBody Long[] brandIds) {
        List<Long> Ids = Arrays.asList(brandIds);
        brandService.removeBatch(Ids);

        return R.ok();
    }


}
