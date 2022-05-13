package quileia.test.test.pojos;

import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "agent_auditory")
public class Auditory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @ManyToOne
  @JoinColumn(name = "agent", nullable = false)
  private TransitAgent agent;
  @ManyToOne
  @JoinColumn(name = "route", nullable = false)
  private TransitRoute route;
  @Column(name = "start_date")
  private ZonedDateTime startDate;
  @Column(name = "end_date")
  private ZonedDateTime endDate;

  public Auditory(){}
  
  public Auditory(TransitAgent agent, TransitRoute route, ZonedDateTime startDate){
    this.agent= agent;
    this.route= route;
    this.startDate= startDate;
  }

  public Auditory(TransitAgent agent, TransitRoute route, ZonedDateTime startDate, ZonedDateTime endDate){
    this.agent= agent;
    this.route= route;
    this.startDate= startDate;
    this.endDate= endDate;
  }

  /**
   * @param agent the agent to set
   */
  public void setAgent(TransitAgent agent) {
    this.agent = agent;
  }

  /**
   * @param route the route to set
   */
  public void setRoute(TransitRoute route) {
    this.route = route;
  }

  /**
   * @param startDate the startDate to set
   */
  public void setStartDate(ZonedDateTime startDate) {
    this.startDate = startDate;
  }

  /**
   * @param endDate the endDate to set
   */
  public void setEndDate(ZonedDateTime endDate) {
    this.endDate = endDate;
  }

  /**
   * @return the agent
   */
  public TransitAgent getAgent() {
    return agent;
  }

  /**
   * @return the route
   */
  public TransitRoute getRoute() {
    return route;
  }

  /**
   * @return the startDate
   */
  public ZonedDateTime getStartDate() {
    return startDate;
  }

  /**
   * @return the endDate
   */
  public ZonedDateTime getEndDate() {
    return endDate;
  }
  
}
