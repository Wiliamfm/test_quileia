package quileia.test.test.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * TransitRoute
 */
@Entity
@Table(name = "transit_route")
public class TransitRoute {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "route_type", nullable = false)
  private RouteType routeType;
  @ManyToOne
  @JoinColumn(name = "street_type", nullable = false)
  private StreetType streetType;
  @Column(name = "number")
  private Integer number;
  @Column(name = "con_level")
  private Double conLevel;
  @OneToOne(mappedBy = "transitRoute")
  private TransitAgent transitAgent;

  public TransitRoute(){}

  public TransitRoute(RouteType routeType, StreetType type, int number, double conLevel) {
    this.routeType= routeType;
    this.streetType= type;
    this.number= number;
    this.conLevel= conLevel;
  }

  /**
   * @param routeType the routeType to set
   */
  public void setRouteType(RouteType routeType) {
    this.routeType = routeType;
  }

  /**
   * @param type the type to set
   */
  public void setType(StreetType type) {
    this.streetType = type;
  }
  
  /**
   * @param number the number to set
   */
  public void setNumber(Integer number) {
    this.number = number;
  }

  /**
   * @param conLevel the conLevel to set
   */
  public void setConLevel(Double conLevel) {
    this.conLevel = conLevel;
  }

  /**
   * @param transitAgent the transitAgent to set
   */
  public void setTransitAgent(TransitAgent transitAgent) {
    this.transitAgent = transitAgent;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @return the routeType
   */
  public RouteType getRouteType() {
    return routeType;
  }

  /**
   * @return the type
   */
  public StreetType getType() {
    return streetType;
  }
  
  /**
   * @return the number
   */
  public Integer getNumber() {
    return number;
  }

  /**
   * @return the conLevel
   */
  public Double getConLevel() {
    return conLevel;
  }
}