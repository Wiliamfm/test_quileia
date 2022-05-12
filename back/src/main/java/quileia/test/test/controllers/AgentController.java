package quileia.test.test.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import quileia.test.test.pojos.TransitAgent;
import quileia.test.test.pojos.TransitRoute;
import quileia.test.test.services.AgentService;
import quileia.test.test.services.RouteService;

@RestController()
@RequestMapping("/agents")
@CrossOrigin
public class AgentController {

  private AgentService agentService;
  private RouteService routeService;

  @Autowired
  public AgentController(AgentService agentService, RouteService routeService){
    this.agentService= agentService;
    this.routeService= routeService;
  }

  @GetMapping
  public List<TransitAgent> getAll(){
    return agentService.getAll();
  }

  @PostMapping
  public TransitAgent create(@RequestBody HashMap<String, String> data){
    try{
      return agentService.save(data.get("code"), data.get("full_name"), Double.parseDouble(data.get("experience_year")), data.get("transit_code"), null);
    }catch(NumberFormatException nfe){
      System.out.println(String.format("Error saving agent {%s} on endpoint with error: {%s}", data.get("full_name"), nfe.getMessage()));
      return null;
    }
  }

  @PostMapping("/{id}/routes")
  public TransitAgent setRoute(@PathVariable("id") String id, @RequestBody HashMap<String, String> data){
    try {
      TransitAgent agent= agentService.getById(id);
      if(agent != null){
        TransitRoute route= routeService.findByAddress(data.get("route_type"), data.get("street_type"), Integer.parseInt(data.get("number")));
        if(route != null && route.getConLevel() > 30){
          agent.setTransitRoute(route);
          return agentService.save(agent);
        }else{
          System.out.println(String.format("The route [%s] was not found", data.get("route_type")));
          return null;
        }
      }else{
        System.out.println(String.format("The agent [%s] do not exists", data.get("agent_id")));
        return null;
      }
    } catch (Exception e) {
      System.out.println(String.format("Error saving agent's route {%s} with error: %s", data.get("route"), e.getMessage()));
      return null;
    }
  }

  @PostMapping("/{id}")
  public TransitAgent delete(@PathVariable("id") String id){
    return agentService.delete(id);
  }
  
}
