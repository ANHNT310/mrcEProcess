package com.bpm.mrceprocess.configuration;

import com.bpm.mrceprocess.property.FeignProperties;
import com.bpm.mrceprocess.property.SecurityProperties;
import com.bpm.utils.FeignErrorDecoder;
import com.bpm.utils.FeignServiceDecoder;
import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
@ConfigurationPropertiesScan(basePackageClasses =  {FeignProperties.class, SecurityProperties.class})
public class FeignClientConfig {

    private static final String API_KEY_HEADER = "x-api-key";
    private static final String API_USER_HEADER = "x-user";

    /**
     * Configures the Feign error decoder.
     *
     * @return a custom ErrorDecoder instance.
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }

    /**
     * Configures the Feign response decoder.
     *
     * @return a custom Decoder instance.
     */
    @Bean
    public Decoder feignDecoder() {
        return new FeignServiceDecoder();
    }

    /**
     * Configures the Feign logger level.
     *
     * @return the logger level for Feign.
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * Configures the Feign request options with connection and read timeouts.
     *
     * @return a Request.Options instance.
     */
    @Bean
    public Request.Options requestOptions(FeignProperties feignProperties) {
        return new Request.Options(
                feignProperties.getConnectionTimeout(),
                TimeUnit.MILLISECONDS,
                feignProperties.getReadTimeout(),
                TimeUnit.MILLISECONDS,
                true
        );
    }

    /**
     * Configures the Feign request interceptor to add headers.
     *
     * @return a RequestInterceptor instance.
     */
    @Bean
    public RequestInterceptor requestInterceptor(SecurityProperties securityProperties) {
        if (securityProperties.getServiceKey() == null || securityProperties.getServiceKey().isEmpty()) {
            throw new IllegalArgumentException("Service key must not be null or empty.");
        }

        return requestTemplate -> {
            requestTemplate.header(API_KEY_HEADER, securityProperties.getServiceKey());

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (attributes != null) {
                HttpServletRequest incomingRequest = attributes.getRequest();

                String userHeaderValue = incomingRequest.getHeader(API_USER_HEADER);
                if (Objects.nonNull(userHeaderValue) && !userHeaderValue.isEmpty()) {
                    requestTemplate.header(API_USER_HEADER, userHeaderValue);
                }
            }
        };
    }
}