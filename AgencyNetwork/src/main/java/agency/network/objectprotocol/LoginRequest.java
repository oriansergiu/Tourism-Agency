package agency.network.objectprotocol;

import agency.network.dto.AgentDTO;

/**
 * Created by Sergiu on 4/2/2017.
 */
public class LoginRequest implements Request{

    private AgentDTO agent;

    public LoginRequest(AgentDTO agent) {
        this.agent = agent;
    }

    public AgentDTO getAgent() {
        return agent;
    }
}
