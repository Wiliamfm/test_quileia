package quileia.test.test.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quileia.test.test.pojos.RouteType;
import quileia.test.test.pojos.StreetType;
import quileia.test.test.pojos.TransitRoute;
import quileia.test.test.repositories.TransitRouteRepository;

@Service
public class RouteService {

  private TransitRouteRepository transitRouteRepository;

  @Autowired
  public RouteService(TransitRouteRepository transitRouteRepository){
    this.transitRouteRepository= transitRouteRepository;
  }

  public List<TransitRoute> getAll(){
    return transitRouteRepository.findAll();
  }

  public TransitRoute create(RouteType routeType, StreetType streetType, int number, double conLevel){
    try {
      if(findByAddress(routeType.getRouteType(), streetType.getType(), number) == null){
        return transitRouteRepository.save(new TransitRoute(routeType, streetType, number, conLevel));
      }else{
        System.out.println(String.format("The route with type [%s] and street [%s] with number [%s] already exists", routeType.getRouteType(), streetType.getType(), number));
        return null;
      }
    } catch (Exception e) {
      return null;
    }
  }

  public TransitRoute findByAddress(String routeType, String streetType, int number){
    Optional<TransitRoute> optional= transitRouteRepository.findByAddress(routeType, streetType, number);
    return optional.isPresent() ? optional.get() : null;
  }

  public TransitRoute delete(long id){
    Optional<TransitRoute> optional= transitRouteRepository.findById(id);
    if(optional.isPresent()){
      transitRouteRepository.delete(optional.get());
      return optional.get();
    }else{
      return null;
    }
  }
  
}
