package quileia.test.test.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import quileia.test.test.pojos.TransitRoute;

@Repository
public interface TransitRouteRepository extends JpaRepository<TransitRoute, Long> {

  @Query("SELECT r FROM TransitRoute as r"
  + " inner join r.routeType as rt with rt.routeType = :route_type"
  + " inner join r.streetType as st with st.type = :street_type"
  + " where r.number = :number")
  public Optional<TransitRoute> findByAddress(@Param("route_type") String routeType, @Param("street_type") String streetType, @Param("number") int number);

}