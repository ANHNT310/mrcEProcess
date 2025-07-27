package com.bpm.mrceprocess.component;

import com.bpm.mrceprocess.security.AuthenticateComponent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditorProvider implements AuditorAware<String> {

    private final AuthenticateComponent authenticateComponent;

    @Override
    public @NonNull Optional<String> getCurrentAuditor() {
        String auditor = (authenticateComponent.getUserId() == null || authenticateComponent.getUserId().isEmpty())
                ? "UNKNOWN"
                : authenticateComponent.getUserId();
        return Optional.of(auditor);
    }
}
