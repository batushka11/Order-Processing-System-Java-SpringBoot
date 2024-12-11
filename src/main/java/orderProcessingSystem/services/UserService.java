package orderProcessingSystem.services;

import orderProcessingSystem.entity.User;

public interface UserService {
    void saveUser(User user);

    User findUserByUsername(String username);
}
