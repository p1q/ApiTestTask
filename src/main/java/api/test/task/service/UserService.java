package api.test.task.service;

import api.test.task.model.User;
import com.mongodb.client.result.DeleteResult;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(User user);

    Optional<User> get(String userId);

    List<User> getAll();

    User update(User user);

    DeleteResult delete(User user);
}
