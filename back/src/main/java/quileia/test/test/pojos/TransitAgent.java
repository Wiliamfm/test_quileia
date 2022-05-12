package quileia.test.test.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * TransitAgent
 */
@Entity(name = "transit_agent")
@JsonIgnoreProperties("hibernateLazyInitializer")
public class TransitAgent {

  @Id
  @Column(name = "id")
  private String code;
  @Column(name = "full_name")
  private String fullName;
  @Column(name = "experience_year")
  private Double experienceYear;
  @Column(name = "transit_code")
  private String transitCode;
  @OneToOne()
  @JoinColumn(name= "transit_route", referencedColumnName = "id")
  private TransitRoute transitRoute;

  public TransitAgent(){}

  public TransitAgent(String code, String fullName, double experienceYear, String transitCode, TransitRoute transitRoute){
    this.code= code;
    this.fullName= fullName;
    this.experienceYear= experienceYear;
    this.transitCode= transitCode;
    this.transitRoute= transitRoute;
  }

  /**
   * @param fullName the fullName to set
   */
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  /**
   * @param code the code to set
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * @param experienceYear the experienceYear to set
   */
  public void setExperienceYear(Double experienceYear) {
    this.experienceYear = experienceYear;
  }

  /**
   * @param transitCode the transitCode to set
   */
  public void setTransitCode(String transitCode) {
    this.transitCode = transitCode;
  }

  /**
   * @param transitRoute the transitRoute to set
   */
  public void setTransitRoute(TransitRoute transitRoute) {
    this.transitRoute = transitRoute;
  }

  /**
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * @return the fullName
   */
  public String getFullName() {
    return fullName;
  }

  /**
   * @return the experienceYear
   */
  public Double getExperienceYear() {
    return experienceYear;
  }

  /**
   * @return the transitCode
   */
  public String getTransitCode() {
    return transitCode;
  }

  /**
   * @return the transitRoute
   */
  public TransitRoute getTransitRoute() {
    return transitRoute;
  }

}