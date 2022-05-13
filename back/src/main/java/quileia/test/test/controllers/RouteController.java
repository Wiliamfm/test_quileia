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

import quileia.test.test.pojos.RouteType;
import quileia.test.test.pojos.StreetType;
import quileia.test.test.pojos.TransitRoute;
import quileia.test.test.services.RouteService;
import quileia.test.test.services.RouteTypeService;
import quileia.test.test.services.StreetService;

@RestController
@RequestMapping("/routes")
@CrossOrigin
public class RouteController {

  private RouteService routeService;
  private RouteTypeService routeTypeService;
  private StreetService streetService;

  @Autowired
  public RouteController (RouteService routeService, RouteTypeService routeTypeService, StreetService streetService){
    this.routeService= routeService;
    this.routeTypeService= routeTypeService;
    this.streetService= streetService;
  }

  @GetMapping
  public List<TransitRoute> getAll(){
    return routeService.getAll();
  }

  @PostMapping TransitRoute create(@RequestBody HashMap<String, String> data){
    try {
      RouteType routeType= routeTypeService.getByName(data.get("route_type"));
      if(routeType != null){
        StreetType streetType= streetService.getByName(data.get("street_type"));
        if(streetType != null){
          if(data.get("id") != null){
            return routeService.update(Long.parseLong(data.get("id")) ,routeType, streetType, Integer.parseInt(data.get("number")), Double.parseDouble(data.get("contingency_level")));
          }else{
            return routeService.create(routeType, streetType, Integer.parseInt(data.get("number")), Double.parseDouble(data.get("contingency_level")));
          }
        }else{
          System.err.println(String.format("Street [%s] not found", data.get("street_type")));
          return null;
        }
      }else{
        System.err.println(String.format("Route type [%s] not found", data.get("route_type")));
        return null;
      }
    } catch (Exception e) {
      System.err.println(String.format("Error saving rout [%s] on post endpoint with error [%s]", data.get("number"), e.getMessage()));
      return null;
    }
  }

  @PostMapping("/{id}")
  public TransitRoute delete(@PathVariable("id") String id){
    return routeService.delete(Long.parseLong(id));
  }
  
}
