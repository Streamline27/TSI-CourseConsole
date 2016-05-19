package services.validation;

import models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import services.dao.course.CourseDAO;

/**
 * Created by Vladislav on 5/19/2016.
 */

@Component
public class CourseValidatorImpl implements CourseValidator {
    @Autowired CourseDAO courseDAO;

    @Override
    public boolean isValid(Course entity) {
        if (entity==null) return false;
        if (entity.getCourseId()==null || entity.getCourseId().equals("")) return false;
        if (entity.getDisciplineTitle()==null || entity.getDisciplineTitle().equals("")) return false;
        if (entity.getLessonNumber()==null || entity.getLessonNumber()<=0) return false;
        if(!entity.getCourseId().matches("[0-9a-fA-F]{2}-[0-9a-fA-F]{3}")) return false;
        if(courseDAO.read(entity.getCourseId())!=null) return false;
        return false;
    }
}
