package orderProcessingSystem.services.impl;

import orderProcessingSystem.entity.User;
import orderProcessingSystem.enums.Role;
import orderProcessingSystem.repository.UserRepository;
import orderProcessingSystem.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(User user) {
        if (findUserByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
       User user = userRepository.findByUsername(username);
       return user;
    }
}
