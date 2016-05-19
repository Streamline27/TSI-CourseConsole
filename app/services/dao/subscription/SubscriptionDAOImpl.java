package services.dao.subscription;

import models.CourseSubscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import services.ModelRowMappers;

import java.util.List;

/**
 * Created by Vladislav on 5/17/2016.
 */
@Component
public class SubscriptionDAOImpl implements SubscriptionDAO {

    private @Autowired JdbcTemplate jdbcTemplate;

    private final String SQL_SELECT_ALL   = "SELECT * FROM CourseSubscription";
    private final String SQL_SELECT = "SELECT * FROM CourseSubscription WHERE subscriptionId=?";
    private final String SQL_INSERT = "INSERT INTO CourseSubscription(courseId, studentId, isPayed) VALUES (?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE CourseSubscription SET courseId=?, studentId=?, isPayed=?  WHERE subscriptionId=?";
    private final String SQL_DELETE = "DELETE FROM CourseSubscription WHERE subscriptionId=?";

    @Override
    public void create(CourseSubscription entity) {
        System.out.println("Created subscription: "+entity.getCourseId() +" "+entity.getStudentId());
        jdbcTemplate.update(SQL_INSERT, entity.getCourseId(), entity.getStudentId(), entity.isPayed());
    }

    @Override
    public CourseSubscription read(Long id) {
        System.out.println("Read subscription: " +id);
        return  (CourseSubscription) jdbcTemplate.queryForObject(SQL_SELECT, new Object[]{ id }, ModelRowMappers.mapSubscription);
    }

    @Override
    public List<CourseSubscription> readAll() {
        System.out.println("Read all subscriptions.");
        return jdbcTemplate.query(SQL_SELECT_ALL, ModelRowMappers.mapSubscription);
    }

    @Override
    public void update(CourseSubscription entity, Long id) {
        System.out.println("Updated subscription. id = "+id);
        jdbcTemplate.update(SQL_UPDATE, entity.getCourseId(), entity.getStudentId(), entity.isPayed(), id);
    }

    @Override
    public void delete(Long id) {
        System.out.println("Deleted subscription. id = "+id);
        jdbcTemplate.update(SQL_DELETE, id);
    }
}
