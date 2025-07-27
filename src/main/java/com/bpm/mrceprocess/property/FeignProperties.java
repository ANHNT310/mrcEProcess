package com.bpm.mrceprocess.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app-feign")
public class FeignProperties {
    private int connectionTimeout = 60000;
    private int readTimeout = 60000;
}
