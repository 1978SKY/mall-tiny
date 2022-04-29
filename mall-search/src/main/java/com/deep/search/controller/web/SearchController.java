package com.deep.search.controller.web;

import com.deep.search.model.params.SearchParam;
import com.deep.search.model.vo.SearchResultVO;
import com.deep.search.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 检索页面
 *
 * @author Deep
 * @date 2022/3/20
 */
@Slf4j
@Api(tags = "商品检索—前台")
@Controller
@RequestMapping("/api/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping({"/search.html"})
    @ApiOperation("检索")
    public String getPage(SearchParam searchParam, Model model) {
        SearchResultVO result = searchService.search(searchParam);

        model.addAttribute("result", result);
        return "search";
    }
}
