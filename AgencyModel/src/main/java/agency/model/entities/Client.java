package agency.model.entities;

/**
 * Created by Sergiu on 3/13/2017.
 */
public class Client implements HasID<Integer> {

    private Integer id;
    private String name;
    private String phoneNumber;

    public Client(Integer id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Client() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer integer) {
        this.id = integer;
    }
}
