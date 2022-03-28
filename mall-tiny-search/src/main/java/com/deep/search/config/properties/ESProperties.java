package com.deep.search.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Elastic Search属性
 *
 * @author Deep
 * @date 2022/3/22
 */
@Data
@ConfigurationProperties(prefix = "es")
public class ESProperties {
    private String host = "124.222.56.141";
    private Integer port = 9200;
}
