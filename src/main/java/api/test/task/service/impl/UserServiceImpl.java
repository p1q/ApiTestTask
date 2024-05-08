package api.test.task.service.impl;

import api.test.task.dao.UserDao;
import api.test.task.exception.IneligibleUserAgeException;
import api.test.task.model.User;
import api.test.task.service.UserService;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User create(User user) {
        if (!isUserAdult(user)) {
            throw new IneligibleUserAgeException();
        }
        return userDao.create(user);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public Optional<User> get(String userId) {
        return userDao.get(userId);
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public DeleteResult delete(User user) {
        return userDao.delete(user);
    }

    private boolean isUserAdult(User user) {
        LocalDate adultDate = user.getBirthdate().plusYears(18);
        return !adultDate.isAfter(LocalDate.now()) || adultDate.isEqual(LocalDate.now());
    }
}
