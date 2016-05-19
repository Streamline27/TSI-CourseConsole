package services.validation;

/**
 * Created by Vladislav on 5/18/2016.
 */
public interface Validator<T> {
    boolean isValid(T entity);

    boolean isPresent(T entity);
}
