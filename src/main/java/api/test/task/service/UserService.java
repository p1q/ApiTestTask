package api.test.task.service;

import api.test.task.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void create(User user);

    Optional<User> get(Long userId);

    List<User> getAll();

    User update(User user);

    void delete(User user);
}
