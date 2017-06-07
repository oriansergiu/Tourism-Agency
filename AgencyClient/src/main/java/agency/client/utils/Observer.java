package agency.client.utils;

/**
 * Created by Sergiu on 3/18/2017.
 */
public interface Observer<E> {
    void update(Observable<E> o);
}
