package api.test.task.dao.impl;

import api.test.task.dao.UserDao;
import api.test.task.model.User;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
    public User create(User user) {
        return mongoTemplate.save(user);
    }

    @Override
    public List<User> getAll() {
        return mongoTemplate.findAll(User.class);
    }

    @Override
    public Optional<User> get(String userId) {
        return Optional.ofNullable(mongoTemplate.findById(userId, User.class));
    }

    @Override
    public List<User> searchUsers(LocalDate fromDate, LocalDate toDate) {
        Query query = new Query();
        query.addCriteria(Criteria.where("birthdate").gte(fromDate).lte(toDate));
        return mongoTemplate.find(query, User.class);
    }

    @Override
    public User update(User user) {
        return mongoTemplate.save(user);
    }

    @Override
    public DeleteResult delete(User user) {
        return mongoTemplate.remove(user);
    }
}
