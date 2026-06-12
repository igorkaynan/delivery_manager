package ordermanager.service;

import ordermanager.dto.UserCreateRequest;
import ordermanager.dto.UserResponse;
import ordermanager.entity.User;
import ordermanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuditLogService auditLogService;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuditLogService auditLogService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.auditLogService = auditLogService;
    }

    public List<UserResponse> listarUsuarios() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                ))
                .toList();
    }

    public UserResponse cadastrarUsuario(UserCreateRequest request) {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        User savedUser = userRepository.save(user);

        auditLogService.saveLog(
                "USER_CREATED",
                "Usuário #" + savedUser.getId() + " criado"
        );

        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }

    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            return;
        }

        userRepository.deleteById(id);

        auditLogService.saveLog(
                "USER_DELETED",
                "Usuário #" + id + " removido"
        );
    }
}