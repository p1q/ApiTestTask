package api.test.task.service.impl;

import api.test.task.dao.UserDao;
import api.test.task.exception.IneligibleUserAgeException;
import api.test.task.model.User;
import api.test.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void create(User user) {
        if (!isUserAdult(user)) {
            throw new IneligibleUserAgeException();
        }
        userDao.create(user);
    }

    @Override
    public Optional<User> get(Long userId) {
        return userDao.get(userId);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Transactional
    @Override
    public User update(User user) {
        userDao.update(user);
        return null;
    }

    @Override
    public void delete(User user) {

    }

    private boolean isUserAdult(User user) {
        LocalDate adultDate = user.getBirthdate().plusYears(18);
        return !adultDate.isAfter(LocalDate.now()) || adultDate.isEqual(LocalDate.now());
    }
}
