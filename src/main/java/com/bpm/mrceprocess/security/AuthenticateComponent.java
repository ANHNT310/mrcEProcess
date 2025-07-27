package com.bpm.mrceprocess.security;

import com.bpm.dtos.SessionUser;
import com.bpm.enums.ApplicationMessage;
import com.bpm.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthenticateComponent {

    public String getUsername() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                SessionUser userInfo = (SessionUser) authentication.getPrincipal();
                if (userInfo != null) {
                    return userInfo.username();
                }
                return null;
            }
        } catch (Exception e) {
            throw new ApplicationException(ApplicationMessage.UNAUTHORIZED);
        }
        return null;
    }

    public String getUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                SessionUser userInfo = (SessionUser) authentication.getPrincipal();
                if (userInfo != null) {
                    return userInfo.id();
                }
                return null;
            }
        } catch (Exception e) {
            throw new ApplicationException(ApplicationMessage.UNAUTHORIZED);
        }
        return null;
    }

    public List<String> getAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        }
        return List.of();
    }
}
