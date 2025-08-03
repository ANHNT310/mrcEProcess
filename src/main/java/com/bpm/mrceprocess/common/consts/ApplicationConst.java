package com.bpm.mrceprocess.common.consts;

public class ApplicationConst {

    public static final String API_KEY_HEADER = "x-api-key";

    public static final String USER_KEY_HEADER = "x-user";

    public static final String WORKFLOW_TENANT = "e-process";

    public static final String[] WHITELISTED_URLS = {
            "/swagger-ui/**",
            "/v3/**",
            "/actuator/**",
            "/token/user",
            "/token/client-service",
    };

    public static final String GENERAL_INFORMATION_PREFIX_CODE = "EP";
}
