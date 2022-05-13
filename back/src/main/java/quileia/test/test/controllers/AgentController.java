package quileia.test.test.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import quileia.test.test.pojos.RouteType;
import quileia.test.test.pojos.StreetType;
import quileia.test.test.pojos.TransitAgent;
import quileia.test.test.pojos.TransitRoute;
import quileia.test.test.services.AgentService;
import quileia.test.test.services.AuditoryService;
import quileia.test.test.services.RouteService;
import quileia.test.test.services.RouteTypeService;
import quileia.test.test.services.StreetService;

@RestController()
@RequestMapping("/agents")
@CrossOrigin
public class AgentController {

  private AgentService agentService;
  private RouteService routeService;
  private AuditoryService auditoryService;
  private RouteTypeService routeTypeService;
  private StreetService streetService;

  @Autowired
  public AgentController(AgentService agentService, RouteService routeService, AuditoryService auditoryService, RouteTypeService routeTypeService, StreetService streetService){
    this.agentService= agentService;
    this.routeService= routeService;
    this.auditoryService= auditoryService;
    this.routeTypeService= routeTypeService;
    this.streetService= streetService;
  }

  @GetMapping
  public List<TransitAgent> getAll(){
    return agentService.getAll();
  }

  @PostMapping
  public TransitAgent create(@RequestBody HashMap<String, String> data){
    try{
      if(data.get("route_id") != null){
        TransitRoute route= routeService.getById(Long.parseLong(data.get("route_id")));
        if(route != null){
          auditoryService.create(data.get("code"), route.getId());
          return agentService.save(data.get("code"), data.get("full_name"), Double.parseDouble(data.get("experience_year")), data.get("transit_code"), route);
        }else{
          return null;
        }
      }else{
        return agentService.save(data.get("code"), data.get("full_name"), Double.parseDouble(data.get("experience_year")), data.get("transit_code"), null);
      }
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
          auditoryService.create(agent.getCode(), route.getId());
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
      System.out.println(String.format("Error saving agent's route {%s} with error: %s", data.get("route_type"), e.getMessage()));
      return null;
    }
  }

  @PostMapping("/{id}")
  public TransitAgent delete(@PathVariable("id") String id){
    return agentService.delete(id);
  }

  @PostMapping("/massiveLoad")
  public List<TransitAgent> createMassive(@RequestParam("agents") MultipartFile agentFile, @RequestParam("routes") MultipartFile routeFile){
    try{
      if(agentFile != null && agentFile.getContentType().equals("text/csv")){
        System.out.println(agentFile.getOriginalFilename());
        System.out.println(String.format("FILE TYPE: {%s}", agentFile.getContentType()));
        File aFile = new File(System.getProperty("java.io.tmpdir")+"/"+agentFile.getOriginalFilename());
        agentFile.transferTo(aFile);
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(aFile))) {
          String line;
          while ((line = br.readLine()) != null) {
              String[] values = line.split(",");
              records.add(Arrays.asList(values));
            }
        }
        for (List<String> list : records) {
          System.out.println(list);
          if(list.size() == 4){
            agentService.save(list.get(0), list.get(1), Double.parseDouble(list.get(2)), list.get(3), null);
          }else if(list.size() == 5){
            TransitRoute route= routeService.getById(Long.parseLong(list.get(5)));
            if(route != null){
              agentService.save(list.get(0), list.get(1), Double.parseDouble(list.get(2)), list.get(3), route);
            }else{
            }
          }else{
            System.out.println(list);
          }
        }
      }
      if(routeFile != null && routeFile.getContentType().equals("text/csv")){
        System.out.println(routeFile.getOriginalFilename());
        System.out.println(String.format("FILE TYPE: {%s}", routeFile.getContentType()));
        File rFile = new File(System.getProperty("java.io.tmpdir")+"/"+routeFile.getOriginalFilename());
        routeFile.transferTo(rFile);
        List<List<String>> records2 = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rFile))) {
          String line;
          while ((line = br.readLine()) != null) {
              String[] values = line.split(",");
              records2.add(Arrays.asList(values));
            }
        }
        for (List<String> list : records2) {
          System.out.println(list);
          if(list.size() == 4){
            RouteType routeType= routeTypeService.getById(Long.parseLong(list.get(0)));
            StreetType streetType= streetService.getById(Long.parseLong(list.get(1)));
            if(routeType != null && streetType != null){
              routeService.create(routeType, streetType, Integer.parseInt(list.get(2)), Double.parseDouble(list.get(3)));
            }else{
              System.out.println("Route or street do not found on file");
            }
          }else{
            System.out.println(list);
          }
        }
      }
      return null;
    }catch(IOException ioe){
      System.err.println(String.format("Error managingn files {%s}", ioe.getMessage()));
      return null;
    }
  }
  
}
