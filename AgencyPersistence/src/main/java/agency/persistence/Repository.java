package agency.persistence;

import java.util.List;

/**
 * Created by Sergiu on 3/13/2017.
 */
public interface Repository<ID, T> {
    int size();
    void save(T entity);
    void delete(ID id);
    void update(T entity);
    T findOne(ID id);
    List<T> findAll();

}
