package com.deep.ware.controller.admin;

import com.deep.common.utils.PageUtils;
import com.deep.common.utils.R;
import com.deep.ware.model.params.WareInfoParam;
import com.deep.ware.service.WareInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 仓库信息
 *
 * @author Deep
 * @date 2022/3/28
 */
@Api(tags = "仓库信息—后台")
@RestController
@RequestMapping("/api/ware/wareinfo")
public class WareInfoController {
    @Autowired
    private WareInfoService wareInfoService;

    @GetMapping("/list")
    @ApiOperation("获取仓库列表")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = wareInfoService.queryPage(params);

        return R.ok().put("page", page);
    }

    @PostMapping("/save")
    @ApiOperation("新增仓库")
    public R save(@RequestBody WareInfoParam wareParam) {
        wareInfoService.save(wareParam.convertTo());

        return R.ok();
    }

    @PostMapping("/update")
    @ApiOperation("修改仓库")
    public R update(@RequestBody WareInfoParam wareParam) {
        wareInfoService.updateById(wareParam.convertTo());

        return R.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("删除仓库")
    public R delete(@RequestBody Long[] ids) {
        // 库存、工作单都依赖仓库id，因此仓库一旦建立就不能简单删除（要判断该仓库下的库存、工作单是否处理完）
//        wareInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
