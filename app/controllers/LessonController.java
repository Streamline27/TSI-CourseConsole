package controllers;

import controllers.utils.ActivePage;
import models.Course;
import models.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Result;
import services.dao.course.CourseDAO;
import services.dao.lesson.LessonDAO;
import views.html.pages.lessons;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;


/**
 * Created by Vladislav on 5/17/2016.
 */
@Controller
public class LessonController {

    @Autowired private CourseDAO courseDAO;
    @Autowired private LessonDAO lessonDAO;


    public Result defaultLessons(){
        List<Course> courseList = courseDAO.readAll();
        List<Lesson> lessonList = getByCourseId(courseList);
        return play.mvc.Controller.ok(lessons.render(lessonList, courseList, null, ActivePage.Lessons));
    }



    private Result getMessagedLessonResult(String msg) {
        List<Course> courseList = courseDAO.readAll();
        List<Lesson> lessonList = lessonDAO.readAll();
        return play.mvc.Controller.ok(lessons.render(lessonList, courseList, msg, ActivePage.Lessons));
    }

    public Result lessons(String id){
        List<Course> courseList = courseDAO.readAll();
        List<Lesson> lessonList = lessonDAO.getByCourseId(id);
        makeCourseWithIdFirstInList(id, courseList);
        return play.mvc.Controller.ok(lessons.render(lessonList, courseList, null, ActivePage.Lessons));
    }

    public Result addLesson(){
        return stayOnPageAndDo(lesson -> lessonDAO.create(lesson));
    }

    public Result updateLesson(){
        String oldLessonId = Form.form().bindFromRequest().get("oldLessonId");
        return stayOnPageAndDo(lesson -> lessonDAO.update(lesson, Long.parseLong(oldLessonId)));
    }

    public Result deleteLesson(){
        String lessonId = Form.form().bindFromRequest().get("lessonId");
        lessonDAO.delete(Long.parseLong(lessonId));

        return play.mvc.Controller.redirect(routes.LessonController.defaultLessons());
    }


    private Result stayOnPageAndDo(Consumer<Lesson> action){
        DynamicForm df = Form.form().bindFromRequest();


        try {
            Date date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(df.get("lessonTime"));
            Lesson lesson = new Lesson(df.get("room"),
                                       new Timestamp(date.getTime()),
                                       df.get("courseId"));
            action.accept(lesson);
            return getMessagedLessonResult(null);

        } catch (ParseException e) {
            e.printStackTrace();
            return getMessagedLessonResult("Unable to create lesson. Bad time format.");
        }
    }

    private void makeCourseWithIdFirstInList(String id, List<Course> courseList) {
        int currentId = 0;
        while (!courseList.get(currentId).getCourseId().equals(id)) currentId++;
        Collections.swap(courseList, 0, currentId);
    }

    private List<Lesson> getByCourseId(List<Course> courseList) {
        if (courseList.size()>0) return lessonDAO.getByCourseId(courseList.get(0).getCourseId());
        else return new ArrayList<>();
    }


}
