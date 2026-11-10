package com.hungtvb.filevault.auth;

public record AuthResponse(String accessToken, String tokenType, long expiresInSeconds) {
}
