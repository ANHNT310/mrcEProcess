package com.bpm.mrceprocess.external.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationUserDTO {
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String title;
    private LocalDateTime syncedAt;
    private String profileType;
}
