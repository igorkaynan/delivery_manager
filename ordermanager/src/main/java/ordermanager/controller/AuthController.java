package ordermanager.controller;

import ordermanager.dto.LoginRequest;
import ordermanager.dto.LoginResponse;
import ordermanager.entity.User;
import ordermanager.repository.UserRepository;
import ordermanager.security.JwtService;
import ordermanager.service.AuditLogService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuditLogService auditLogService;

    public AuthController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuditLogService auditLogService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.auditLogService = auditLogService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {

        Optional<User> userOptional =
                userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            return new LoginResponse("Usuário ou senha inválidos");
        }

        User user = userOptional.get();

        boolean passwordValid = passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword()
        );

        if (!passwordValid) {
            return new LoginResponse("Usuário ou senha inválidos");
        }

        String token = jwtService.gerarToken(user.getEmail());

        auditLogService.saveLog(
                "LOGIN",
                "Usuário " + user.getEmail() + " realizou login"
        );

        return new LoginResponse(token);
    }
}