package com.hungtvb.filevault.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hungtvb.filevault.config.JwtProperties;
import io.jsonwebtoken.JwtException;
import java.time.Duration;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class JwtServiceTest {

    private static final String SECRET_A = "secret-A-0123456789-0123456789-abcdef";
    private static final String SECRET_B = "secret-B-0123456789-0123456789-abcdef";

    private JwtService service(String secret, Duration ttl) {
        return new JwtService(new JwtProperties(secret, ttl));
    }

    @Test
    void roundTrip() {
        JwtService s = service(SECRET_A, Duration.ofMinutes(15));
        UUID id = UUID.randomUUID();
        assertEquals(id, s.parse(s.generate(id)));
    }

    @Test
    void tokenSignedWithOtherSecretIsRejected() {
        String token = service(SECRET_A, Duration.ofMinutes(15)).generate(UUID.randomUUID());
        assertThrows(JwtException.class, () -> service(SECRET_B, Duration.ofMinutes(15)).parse(token));
    }

    @Test
    void expiredTokenIsRejected() {
        JwtService s = service(SECRET_A, Duration.ofSeconds(-10));
        String token = s.generate(UUID.randomUUID());
        assertThrows(JwtException.class, () -> s.parse(token));
    }
}
