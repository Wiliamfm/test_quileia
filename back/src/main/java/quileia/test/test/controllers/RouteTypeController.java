package quileia.test.test.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import quileia.test.test.pojos.RouteType;
import quileia.test.test.services.RouteTypeService;

@RestController
@RequestMapping("/route_types")
@CrossOrigin
/**
 * RouteTypeController
 */
public class RouteTypeController {

  private RouteTypeService routeTypeService;

  public RouteTypeController(RouteTypeService routeTypeService){
    this.routeTypeService= routeTypeService;
  }
  
  @GetMapping
  public List<RouteType> getAll(){
    return routeTypeService.getAll();
  }
  
}