package com.hungtvb.filevault.user;

import com.hungtvb.filevault.common.InvalidCredentialsException;
import java.util.UUID;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/me")
public class MeController {

    private final UserRepository users;

    public MeController(UserRepository users) {
        this.users = users;
    }

    @GetMapping
    public UserResponse me(@AuthenticationPrincipal UUID userId) {
        return users.findById(userId)
                .map(UserResponse::from)
                .orElseThrow(InvalidCredentialsException::new); // token hợp lệ nhưng user đã bị xóa
    }
}
