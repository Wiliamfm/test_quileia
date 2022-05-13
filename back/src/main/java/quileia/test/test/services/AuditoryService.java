package quileia.test.test.services;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quileia.test.test.pojos.Auditory;
import quileia.test.test.pojos.TransitAgent;
import quileia.test.test.pojos.TransitRoute;
import quileia.test.test.repositories.AuditoryRepository;

@Service
public class AuditoryService {

  private AuditoryRepository auditoryRepository;
  private AgentService agentService;
  private RouteService routeService;

  @Autowired
  public AuditoryService(AuditoryRepository auditoryRepository, AgentService agentService, RouteService routeService){
    this.auditoryRepository= auditoryRepository;
    this.agentService= agentService;
    this.routeService= routeService;
  }

  public Auditory create(String agentId, long routeId){
    TransitAgent a= agentService.getById(agentId);
    if(a != null){
      TransitRoute r= routeService.getById(routeId);
      if(r != null){
        Auditory auditory = new Auditory(a, r, ZonedDateTime.now());
        return auditoryRepository.save(auditory);
      }else{
        return null;
      }
    }else{
      return null;
    }
  }

  public List<Auditory> getByAgent(String id){
    Optional<List<Auditory>> optional= auditoryRepository.findByAgent(id);
    return optional.isPresent()? optional.get() : null;
  }

  public List<Auditory> getByRoute(long id){
    Optional<List<Auditory>> optional= auditoryRepository.findByRoute(id);
    return optional.isPresent()? optional.get() : null;
  }
  
}
