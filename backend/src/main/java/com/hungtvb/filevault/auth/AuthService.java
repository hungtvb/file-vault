package com.hungtvb.filevault.auth;

import com.hungtvb.filevault.common.EmailAlreadyUsedException;
import com.hungtvb.filevault.common.InvalidCredentialsException;
import com.hungtvb.filevault.user.User;
import com.hungtvb.filevault.user.UserRepository;
import com.hungtvb.filevault.user.UserResponse;
import java.util.Locale;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthService(UserRepository users, PasswordEncoder encoder, JwtService jwt) {
        this.users = users;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    @Transactional
    public UserResponse register(RegisterRequest req) {
        String email = normalize(req.email());
        if (users.existsByEmail(email)) {
            throw new EmailAlreadyUsedException();
        }
        User u = new User();
        u.setEmail(email);
        u.setPasswordHash(encoder.encode(req.password()));
        return UserResponse.from(users.save(u));
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest req) {
        // Cùng một lỗi cho "sai email" và "sai mật khẩu" để không lộ email nào tồn tại
        User u = users.findByEmail(normalize(req.email())).orElseThrow(InvalidCredentialsException::new);
        if (!encoder.matches(req.password(), u.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }
        return new AuthResponse(jwt.generate(u.getId()), "Bearer", jwt.accessTtlSeconds());
    }

    private static String normalize(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
