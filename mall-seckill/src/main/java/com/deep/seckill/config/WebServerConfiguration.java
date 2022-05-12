package com.deep.seckill.config;

import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

/**
 * 定制内嵌Tomcat配置
 *
 * @author Deep
 * @date 2022/5/9
 */
@Configuration
public class WebServerConfiguration implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        ((TomcatServletWebServerFactory) factory).addConnectorCustomizers(connector -> {
            Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
            // 超过30秒将自动断开keepAlive连接
            protocol.setKeepAliveTimeout(10000);
            // 用户请求超过10000个请求将断开keepAlive连接
            protocol.setMaxKeepAliveRequests(10000);
        });
    }
}
