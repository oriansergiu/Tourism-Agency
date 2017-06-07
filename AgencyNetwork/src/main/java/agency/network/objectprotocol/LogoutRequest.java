package agency.network.objectprotocol;

import agency.network.dto.AgentDTO;

/**
 * Created by Sergiu on 4/2/2017.
 */
public class LogoutRequest implements Request {
    private AgentDTO agentDTO;

    public LogoutRequest(AgentDTO agentDTO) {
        this.agentDTO = agentDTO;
    }

    public AgentDTO getAgentDTO() {
        return agentDTO;
    }
}
