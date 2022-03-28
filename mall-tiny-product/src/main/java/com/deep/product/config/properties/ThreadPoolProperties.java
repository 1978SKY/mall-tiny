package com.deep.product.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 线程池配置文件
 *
 * @author Deep
 * @date 2022/3/26
 */
@Data
@ConfigurationProperties(prefix = "mall.thread.pool")
public class ThreadPoolProperties {
    private Integer coreSize = 10;
    private Integer maxSize = 10;
    private Integer keepAliveTime = 200;
}
