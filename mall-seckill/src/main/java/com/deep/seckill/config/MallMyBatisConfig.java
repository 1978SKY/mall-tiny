package com.deep.seckill.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis Plus配置类
 *
 * @author Deep
 * @date 2022/4/17
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.deep.seckill.dao")
public class MallMyBatisConfig {
    /**
     * 分页插件
     *
     * @return 分页拦截器
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {

        PaginationInterceptor interceptor = new PaginationInterceptor();
        // 请求页大于最后一页，返回到首页
        interceptor.setOverflow(true);
        // 每页最大限制数量（比如：前端请求 2000条/页，也只能返回 500条/页）
        interceptor.setLimit(500);

        return interceptor;
    }
}
