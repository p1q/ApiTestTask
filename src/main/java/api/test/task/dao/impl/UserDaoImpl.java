package api.test.task.dao.impl;

import api.test.task.dao.UserDao;
import api.test.task.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @Override
    public void add(User user) {
        //sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public Optional<User> get(Long userId) {
        //User user = sessionFactory.getCurrentSession().get(User.class, userId);
        //return Optional.ofNullable(user);
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }
}
