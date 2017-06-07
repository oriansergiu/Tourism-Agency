package agency.model.entities;

/**
 * Created by Sergiu on 3/13/2017.
 */
public class Agent implements HasID<Integer> {

    private Integer id;
    private String user;
    private String password;
    private String name;

    public Agent(Integer id, String user, String password, String name) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.name = name;
    }

    public Agent(String username, String pass) {
        this.user = username;
        this.password = pass;
    }

    public Agent() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer integer) {
        this.id = integer;
    }
}
