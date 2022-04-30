package com.deep.ware.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 线程池配置文件
 *
 * @author Deep
 * @date 2022/4/10
 */
@Data
@ConfigurationProperties(prefix = "ware.thread.pool")
public class ThreadPoolConfigProperties {
    private Integer coreSize = 10;
    private Integer maxSize = 30;
    private Integer keepAliveTime = 20;
}
