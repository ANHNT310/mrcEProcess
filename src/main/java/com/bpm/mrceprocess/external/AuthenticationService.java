package com.bpm.mrceprocess.external;

import com.bpm.mrceprocess.external.payload.AuthenticationUserDTO;
import com.bpm.mrceprocess.configuration.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "authentication-service", url = "${app-feign.services.authentication}", path = "/authentication",
        configuration = FeignClientConfig.class)
public interface AuthenticationService {

    @GetMapping("/user/{id}")
    AuthenticationUserDTO userById(@PathVariable String id);
}
