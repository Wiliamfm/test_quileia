package quileia.test.test.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import quileia.test.test.pojos.RouteType;

@Repository
public interface RouteTypeRepository extends JpaRepository<RouteType, Long> {

  @Query("SELECT rt FROM RouteType rt WHERE rt.routeType = :routeType")
  public Optional<RouteType> findByRouteType(@Param("routeType") String routeType);
  
}
