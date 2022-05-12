package quileia.test.test.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import quileia.test.test.pojos.StreetType;

@Repository
public interface StreetRepository extends JpaRepository<StreetType, Long> {

  @Query("SELECT st FROM StreetType st WHERE st.type = ?1")
  public Optional<StreetType> findByType(String type);
  
}
