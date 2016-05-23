package controllers;

import controllers.utils.ActivePage;
import models.Course;
import models.CourseSubscription;
import models.Student;
import models.misc.SubscriptionTuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import play.data.Form;
import play.mvc.Result;
import services.dao.course.CourseDAO;
import services.dao.student.StudentDAO;
import services.dao.subscription.SubscriptionDAO;
import services.validation.SubscriptionValidator;
import views.html.pages.subscriptions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by Vladislav on 5/15/2016.
 */

@Controller
public class SubscriptionController {

    @Autowired private SubscriptionDAO subscriptionDAO;
    @Autowired private StudentDAO studentDAO;
    @Autowired private CourseDAO courseDAO;
    @Autowired private SubscriptionValidator subscriptionValidator;

    public Result subscriptions(){
        return getDefaultSubscriptionPageResult();
    }

    public Result addSubscription(){
        CourseSubscription subscription = Form.form(CourseSubscription.class).bindFromRequest().get();

        if (subscriptionValidator.isValid(subscription) && !subscriptionValidator.isPresent(subscription)){
            subscriptionDAO.create(subscription);
            return getDefaultSubscriptionPageResult();
        }

        return getMessagedSubscriptionPageResult("Could not create subscription. Bad credentials.");
    }

    public Result updateSubscription(){
        CourseSubscription subscription = Form.form(CourseSubscription.class).bindFromRequest().get();

        CourseSubscription subscriptionToUpdate = subscriptionDAO.read(subscription.getSubscriptionId());
        subscriptionToUpdate.setPayed(subscription.isPayed());
        subscriptionDAO.update(subscriptionToUpdate, subscription.getSubscriptionId());
        return getDefaultSubscriptionPageResult();
    }

    public Result deleteSubscription(){
        CourseSubscription subscription = Form.form(CourseSubscription.class).bindFromRequest().get();
        subscriptionDAO.delete(subscription.getSubscriptionId());
        return getDefaultSubscriptionPageResult();
    }

    private Result getDefaultSubscriptionPageResult(){
        return getMessagedSubscriptionPageResult(null);
    }

    private Result getMessagedSubscriptionPageResult(String errorMessage){
        List<Student> studentList = studentDAO.readAll();
        List<Course> courseList = courseDAO.readAll();
        List<CourseSubscription> courseSubscriptions = subscriptionDAO.readAll();
        List<SubscriptionTuple> subscriptionList = getSubscriptionTupleList(courseSubscriptions, studentList, courseList);
        return play.mvc.Controller.ok(subscriptions.render(subscriptionList, studentList, courseList, errorMessage, ActivePage.Subscriptions));
    }

    private Predicate<Course> courseIdEquals(String id){
        return course -> course.getCourseId().equals(id);
    }

    private Predicate<Student> studentIdEquals(String id){
        return student -> student.getStudentId().equals(id);
    }

    private List<SubscriptionTuple> getSubscriptionTupleList(List<CourseSubscription> courseSubscriptions,
                                                             List<Student> studentList,
                                                             List<Course> courseList){
        List<SubscriptionTuple> subscriptionTuples = new ArrayList<>();
        for (CourseSubscription s : courseSubscriptions){
            Course subscribedCourse = courseList.stream()
                    .filter(courseIdEquals(s.getCourseId()))
                    .findFirst().get();
            Student subscribedStudent  = studentList.stream()
                    .filter(studentIdEquals(s.getStudentId()))
                    .findFirst().get();

            SubscriptionTuple tuple = new SubscriptionTuple(s, subscribedStudent, subscribedCourse);

            subscriptionTuples.add(tuple);
        }
        return subscriptionTuples;
    }


}
