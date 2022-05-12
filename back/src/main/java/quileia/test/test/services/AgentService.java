package quileia.test.test.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quileia.test.test.pojos.TransitAgent;
import quileia.test.test.pojos.TransitRoute;
import quileia.test.test.repositories.TransitAgentRepository;

@Service
public class AgentService {

  private TransitAgentRepository transitAgentRepository;

  @Autowired
  public AgentService(TransitAgentRepository transitAgentRepository){
    this.transitAgentRepository= transitAgentRepository;
  }

  /**
   * Update the given agent.
   * @param agent the agent to update
   * @return the updated agent. Null otherway.
   */
  public TransitAgent save(TransitAgent agent){
    try {
      return transitAgentRepository.save(agent);
    } catch (Exception e) {
      System.out.println(String.format("Error saving agent {%s} with error: {%s}", agent.getFullName(), e.getMessage()));
      return null;
    }
  }

  public TransitAgent save(String code, String fullName, double exYear, String tranCode, TransitRoute route){
    try{
      return transitAgentRepository.save(new TransitAgent(code, fullName, exYear, tranCode, route));
    }catch(Exception e){
      System.out.println(String.format("Error saving agent {%s} with error: {%s}", fullName, e.getMessage()));
      return null;
    }
  }

  public List<TransitAgent> getAll(){
    return transitAgentRepository.findAll();
  }

  public TransitAgent getById(String id){
    return transitAgentRepository.getById(id);
  }

  public TransitAgent delete(String id){
    try {
      TransitAgent agent= getById(id);
      transitAgentRepository.delete(agent);
      return agent;
    } catch (EntityNotFoundException enfe) {
      System.err.println(String.format("The agent with id [%s] do not exists", id));
      return null;
    }
  }
  
}
