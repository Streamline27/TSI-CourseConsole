package controllers;

import controllers.utils.ActivePage;
import models.Course;
import models.Discipline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import play.data.Form;
import play.mvc.Result;
import services.dao.course.CourseDAO;
import services.dao.discipline.DisciplineDAO;
import services.validation.CourseValidator;
import views.html.pages.courses;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Vladislav on 5/8/2016.
 */

@Controller
public class CoursesController {

    @Autowired private CourseDAO courseDAO;
    @Autowired private DisciplineDAO disciplineDAO;
    @Autowired private CourseValidator courseValidator;

    public Result courses(){
        return getDefaultCoursePageResult();
    }

    public Result addCourse(){
        Form<Course> form = Form.form(Course.class).bindFromRequest();
        Course course = form.get();

        System.out.println(course.getCourseId());
        System.out.println(course.getDisciplineTitle());
        System.out.println(course.getFinished());
        System.out.println(course.getLessonNumber());
        System.out.println(course.getPrice());

        if (courseValidator.isValid(course) && !courseValidator.isPresent(course)){
            courseDAO.create(course);
            return getDefaultCoursePageResult();
        }
        return getMessagedCoursePageResult("Could not create course. Bad credentials.");

    }

    public Result updateCourse(){
        Form<Course> form = Form.form(Course.class).bindFromRequest();
        Course course = form.get();

        if (courseValidator.isValid(course)){
            String oldStudentId = Form.form().bindFromRequest().get("oldCourseId");
            courseDAO.update(course, oldStudentId);
            return getDefaultCoursePageResult();
        }
        else return getMessagedCoursePageResult("Could not update course. Bad credentials.");

    }

    public Result deleteCourse()
    {
        Form<Course> form = Form.form(Course.class).bindFromRequest();
        courseDAO.delete(form.get().getCourseId());
        return getDefaultCoursePageResult();
    }

    private Result getMessagedCoursePageResult(String message){
        List<Course> courseList = courseDAO.readAll();
        List<Discipline> disciplineList = disciplineDAO.readAll();
        return play.mvc.Controller.ok(courses.render(courseList, disciplineList, message, ActivePage.Courses));
    }

    private Result getDefaultCoursePageResult(){
        return getMessagedCoursePageResult(null);
    }
}
