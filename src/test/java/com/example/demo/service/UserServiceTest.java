package com.example.demo.service;

import jakarta.transaction.Transactional;
import com.example.demo.Dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void loadUserByUsername() {
        String email = "user_" + System.nanoTime() + "@test.com";

        User user = new User();
        user.setEmail(email);
        user.setUsername("testuser");
        user.setPassword(passwordEncoder.encode("12345"));
        userRepository.save(user);

        UserDetails userDetails =
                userService.loadUserByUsername(email);

        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(email, userDetails.getUsername());
    }

    @Test
    void register() {

        String email = "register_" + System.nanoTime() + "@test.com";

        UserDto dto = new UserDto();
        dto.setEmail(email);
        dto.setUsername("Oralbek");
        dto.setPassword("qwertyuiop");

        userService.register(dto);

        User savedUser = userRepository.findByEmail(email);

        Assertions.assertNotNull(savedUser);
        Assertions.assertTrue(
                passwordEncoder.matches("qwertyuiop", savedUser.getPassword())
        );
        Assertions.assertEquals(1, savedUser.getPermissions().size());
        Assertions.assertEquals(
                "USER",
                savedUser.getPermissions().get(0).getName()
        );
    }

    @Test
    void getAll() {
        User user1 = new User();
        user1.setEmail("a_" + System.nanoTime() + "@test.com");
        userRepository.save(user1);

        User user2 = new User();
        user2.setEmail("b_" + System.nanoTime() + "@test.com");
        userRepository.save(user2);

        List<User> users = userService.getAll();

        Assertions.assertTrue(users.size() >= 2);
    }
}
