package quileia.test.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import quileia.test.test.pojos.TransitAgent;

@Repository
public interface TransitAgentRepository extends JpaRepository<TransitAgent, String> {
  
}
