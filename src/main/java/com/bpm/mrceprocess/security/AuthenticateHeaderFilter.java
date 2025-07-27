package com.bpm.mrceprocess.security;

import com.bpm.dtos.SessionUser;
import com.bpm.enums.ApplicationMessage;
import com.bpm.exception.ApplicationException;
import com.bpm.mrceprocess.property.SecurityProperties;
import com.bpm.utils.JacksonUtils;
import com.bpm.utils.SessionUserUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import static com.bpm.mrceprocess.common.consts.ApplicationConst.*;

@Slf4j
public class AuthenticateHeaderFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public AuthenticateHeaderFilter(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI().substring(request.getContextPath().length());
        for (String pattern : WHITELISTED_URLS) {
            if (pathMatcher.match(pattern, path)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        String apiKey = request.getHeader(API_KEY_HEADER);
        if (StringUtils.isEmpty(apiKey) || !apiKey.equals(securityProperties.getServiceKey())) {
            throw new ApplicationException(ApplicationMessage.UNAUTHORIZED);
        }

        String userHeaderValue = request.getHeader(USER_KEY_HEADER);
        if (userHeaderValue == null || userHeaderValue.isEmpty() || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            Authentication authentication = decodeHeaderToSessionUser(userHeaderValue);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            log.error("Failed to process x-user header. Clearing security context.", e);
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private Authentication decodeHeaderToSessionUser(String headerValue) throws IOException {
        byte[] jsonBytes = Base64.getDecoder().decode(headerValue);
        SessionUser userInfo = (SessionUser) JacksonUtils.parseObject(jsonBytes, SessionUser.class);
        if (userInfo != null) {
            List<SimpleGrantedAuthority> authorities = userInfo.authorities().stream().map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            return new UsernamePasswordAuthenticationToken(userInfo, (Object)null, authorities);
        } else {
            return null;
        }
    }
}
