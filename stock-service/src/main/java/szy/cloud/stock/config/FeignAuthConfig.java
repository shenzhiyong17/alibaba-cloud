package szy.cloud.stock.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class FeignAuthConfig implements RequestInterceptor {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String header = UUID.randomUUID().toString();
        requestTemplate.header("token", header);
        logger.info("FeignAuthConfig");
    }
}
