package api.test.task.dao;

import api.test.task.model.User;
import com.mongodb.client.result.DeleteResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    User create(User user);

    Optional<User> get(String userId);

    List<User> getAll();

    List<User> searchUsers(LocalDate fromDate, LocalDate toDate);

    User update(User user);

    DeleteResult delete(User user);
}
