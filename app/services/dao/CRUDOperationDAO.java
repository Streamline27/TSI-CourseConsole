package services.dao;

import models.Student;

import java.util.List;

/**
 * Created by Vladislav on 5/7/2016.
 */
public interface CRUDOperationDAO<T, K> {
    void create(T entity);

    T read(K id);

    List<T> readAll();

    void update(T entity, K id);

    void delete(K id);
}
