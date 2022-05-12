package quileia.test.test.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quileia.test.test.pojos.RouteType;
import quileia.test.test.repositories.RouteTypeRepository;

@Service
public class RouteTypeService {

  private RouteTypeRepository routeTypeRepository;

  @Autowired
  public RouteTypeService(RouteTypeRepository routeTypeRepository){
    this.routeTypeRepository= routeTypeRepository;
  }

  public List<RouteType> getAll(){
    return routeTypeRepository.findAll();
  }

  public RouteType getByName(String name){
    Optional<RouteType> optional= routeTypeRepository.findByRouteType(name);
    return optional.isPresent() ? optional.get() : null;
  }

  public RouteType save(RouteType routeType){
    try{
      return routeTypeRepository.save(routeType);
    }catch(Exception e){
      System.out.println("Error saving routeType" + e.getMessage());
      return null;
    }
  }
  
}
