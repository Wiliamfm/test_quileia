package quileia.test.test.pojos;

import java.util.Set;

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
@Table(name = "street_type")
public class StreetType {

  @Id
  //@GeneratedValue(generator = "testDataSequenceGenerator")
  private Long id;
  @Column(name = "type")
  private String type;
  @OneToMany(mappedBy = "streetType")
  private Set<TransitRoute> transitRoutes;

  public StreetType(){}

  public StreetType(String type){
    this.type= type;
  }

  /**
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @return the type
   */
  public String getType() {
    return type;
  }
  
}
