package agency.model.entities;

/**
 * Created by Sergiu on 3/13/2017.
 */
public interface HasID<ID> {
    ID getId();
    void setId(ID id);
}
