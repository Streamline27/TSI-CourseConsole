package services.dao.discipline;

import models.Discipline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import services.ModelRowMappers;

import java.util.List;

/**
 * Created by Vladislav on 5/17/2016.
 */

@Component
public class DisciplineDAOImpl implements DisciplineDAO {
    @Autowired private JdbcTemplate jdbcTemplate;

    private final String SQL_INSERT = "INSERT IGNORE INTO Discipline(Title) VALUES (?)";

    private final String SQL_SELECT_ALL   = "SELECT * FROM Discipline";
    private final String SQL_SELECT = "SELECT * FROM Discipline WHERE title=?";
    private final String SQL_UPDATE = "UPDATE Discipline SET title=? WHERE title=?";
    private final String SQL_DELETE = "DELETE FROM Discipline WHERE title=?";

    @Override
    public void create(Discipline entity) {
        System.out.println("Created discipline: "+entity.getTitle());
        jdbcTemplate.update(SQL_INSERT, entity.getTitle());
    }

    @Override
    public Discipline read(String title) {
        System.out.println("Read discipline with title = " +title);
        try {
            return  (Discipline) jdbcTemplate.queryForObject(SQL_SELECT, new Object[]{ title }, ModelRowMappers.mapDiscipline);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<Discipline> readAll() {
        System.out.println("Read all disciplines.");
        try {
            return jdbcTemplate.query(SQL_SELECT_ALL, ModelRowMappers.mapDiscipline);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public void update(Discipline entity, String id) {
        System.out.println("Updated discipline. title = "+entity.getTitle());
        jdbcTemplate.update(SQL_UPDATE, entity.getTitle(), id);
    }

    @Override
    public void delete(String id) {
        System.out.println("Deleted discipline. title = "+id);
        jdbcTemplate.update(SQL_DELETE, id);
    }
}
