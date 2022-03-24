package com.deep.search.service;

import com.deep.search.model.params.SearchParam;
import com.deep.search.model.vo.SearchResultVO;

/**
 * 检索服务
 *
 * @author Deep
 * @date 2022/3/21
 */
public interface SearchService {
    /**
     * 检索
     */
    SearchResultVO search(SearchParam searchParam);
}
