package com.project.web.configs;


import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

/**
 * Created by rohitgupta on 2016/01/09.
 */

@Data
@ConfigurationProperties(prefix = "service", ignoreUnknownFields = true)
public class ServiceVersionProperties {
    Logger logger = LoggerFactory.getLogger(getClass());

    private String version = "0.0.1-RELEASE";

    public String getVersion() {
        return version;
    }

    @PostConstruct
    public void log() {
        if (logger.isDebugEnabled())
            logger.debug("Configured {},  {}", getClass(), toString());
        else if (logger.isInfoEnabled())
            logger.info("Configured {}", getClass());
    }
}
