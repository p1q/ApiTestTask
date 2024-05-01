package api.test.task.dao;

import api.test.task.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void add(User user);

    Optional<User> get(Long userId);

    List<User> getAll();

    void update(User user);

    void delete(User user);
}