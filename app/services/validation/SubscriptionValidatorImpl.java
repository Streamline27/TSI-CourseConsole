package services.validation;

import models.CourseSubscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import services.dao.subscription.SubscriptionDAO;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Vladislav on 5/19/2016.
 */

@Component
public class SubscriptionValidatorImpl implements SubscriptionValidator {

    @Autowired private SubscriptionDAO subscriptionDAO;

    @Override
    public boolean isValid(CourseSubscription entity) {
        if (entity == null) return false;
        if (entity.getCourseId() == null || entity.getCourseId().trim().equals("")) return false;
        if (entity.getStudentId() == null || entity.getStudentId().trim().equals("")) return false;
        return true;
    }

    @Override
    public boolean isPresent(CourseSubscription entity) {
        return subscriptionIsAlreadyPresent(entity);
    }

    private Predicate<CourseSubscription> subscriptionEquals(CourseSubscription entity){
        return subscription -> subscription.getStudentId().equals(entity.getStudentId()) &&
                               subscription.getCourseId().equals(entity.getCourseId());
    }

    private boolean subscriptionIsAlreadyPresent(CourseSubscription entity) {
        List<CourseSubscription> subscriptions = subscriptionDAO.readAll();
        return subscriptions.stream().anyMatch(subscriptionEquals(entity));
    }

}
