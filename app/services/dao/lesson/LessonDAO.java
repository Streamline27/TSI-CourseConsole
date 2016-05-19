package services.dao.lesson;

import models.Lesson;
import services.dao.CRUDOperationDAO;

import java.util.List;

/**
 * Created by Vladislav on 5/17/2016.
 */
public interface LessonDAO extends CRUDOperationDAO<Lesson, Long> {
    List<Lesson> getByCourseId(String id);
}
