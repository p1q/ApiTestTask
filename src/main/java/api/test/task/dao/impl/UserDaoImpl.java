package api.test.task.dao.impl;

import api.test.task.dao.UserDao;
import api.test.task.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void create(User user) {
        mongoTemplate.save(user);
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
