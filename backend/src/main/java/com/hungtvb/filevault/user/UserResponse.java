package com.hungtvb.filevault.user;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(UUID id, String email, boolean mfaEnabled, Instant createdAt) {
    public static UserResponse from(User u) {
        return new UserResponse(u.getId(), u.getEmail(), u.isMfaEnabled(), u.getCreatedAt());
    }
}
