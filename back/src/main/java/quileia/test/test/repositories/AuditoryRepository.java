package quileia.test.test.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import quileia.test.test.pojos.Auditory;

@Repository
public interface AuditoryRepository extends JpaRepository<Auditory, Long> {

  @Query("select a from Auditory a where a.agent.id = :agent_id")
  public Optional<List<Auditory>> findByAgent(@Param("agent_id") String agentId);
  
  @Query("select a from Auditory a where a.route.id = :route_id")
  public Optional<List<Auditory>> findByRoute(@Param("route_id") long id);

}
