package agency.client.utils;

/**
 * Created by Sergiu on 3/18/2017.
 */
public interface Observable<E> {
    void addObserver(Observer<E> o);
    void removeObserver(Observer<E> o);
    void notifyObserver();
}
