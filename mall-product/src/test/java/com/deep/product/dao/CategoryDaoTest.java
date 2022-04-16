package com.deep.product.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deep.product.model.entity.CategoryEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * @author Deep
 * @date 2022/3/17
 */
@Slf4j
@SpringBootTest
class CategoryDaoTest {
    @Autowired
    private CategoryDao categoryDao;


    @Test
    void logicDelete() {
        Long[] ids = {1433L};
        int count = categoryDao.logicDelete(Arrays.asList(ids));
        log.info("共执行{}行", count);
    }


    /**
     * QueryWrapper性能测试
     */
    @Test
    void selectQPSTest() {
        double count = 0.0;
        List<CategoryEntity> list = null;
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            QueryWrapper<CategoryEntity> wrapper = new QueryWrapper<>();
            for (int j = 1; j < 1423; j++) {
                wrapper.eq("cat_id", j).or();
            }
            list = categoryDao.selectList(wrapper);
            long end = System.currentTimeMillis();
            count += end - start;
            log.info("共耗时：{}ms", (end - start));
        }
        log.info("list共包含{}条记录", list.size());
        log.info("平均耗时：{}ms", (count / 10.0));  // 平均243.6ms 最长1068ms
    }
}