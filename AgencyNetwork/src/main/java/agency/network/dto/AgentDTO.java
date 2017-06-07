package agency.network.dto;

import java.io.Serializable;

/**
 * Created by Sergiu on 4/2/2017.
 */
public class AgentDTO implements Serializable {

    private Integer id;
    private String user;
    private String password;
    private String name;

    public AgentDTO(String user, String password) {
        this.user = user;
        this.password = password;
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

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) { this.id = id; };

}
