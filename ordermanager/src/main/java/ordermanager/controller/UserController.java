package ordermanager.controller;

import ordermanager.dto.UserResponse;
import ordermanager.entity.User;
import ordermanager.service.UserService;
import org.springframework.web.bind.annotation.*;

import ordermanager.dto.UserCreateRequest;
import ordermanager.dto.UserResponse;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping
    public List<UserResponse> listarUsuarios() {
        return userService.listarUsuarios();
    }

    @PostMapping
    public UserResponse cadastrarUsuario(
            @RequestBody UserCreateRequest request
    ) {
        return userService.cadastrarUsuario(request);
    }

}