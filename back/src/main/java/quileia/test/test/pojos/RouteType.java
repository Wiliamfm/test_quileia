package quileia.test.test.pojos;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "route_type")
public class RouteType {

  @Id
  //@GeneratedValue(generator = "testDataSequenceGenerator1")
  private Long id;
  @Column(name = "route_type")
  private String routeType;
  @OneToMany(mappedBy = "routeType", cascade = CascadeType.REMOVE)
  private Set<TransitRoute> transitRoutes;

  public RouteType(){}

  public RouteType(String routeType){
    this.routeType= routeType;
  }

  /**
   * @param routeType the routeType to set
   */
  public void setRouteType(String routeType) {
    this.routeType = routeType;
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
  public String getRouteType() {
    return routeType;
  }
  
}
