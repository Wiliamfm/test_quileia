package quileia.test.test.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import quileia.test.test.pojos.StreetType;
import quileia.test.test.services.StreetService;

@RestController
@RequestMapping("/street_types")
@CrossOrigin
/**
 * StreetTypeController
 */
public class StreetTypeController {

  private StreetService streetService;

  public StreetTypeController(StreetService streetService){
    this.streetService= streetService;
  }

  @GetMapping
  public List<StreetType> getAll(){
    return streetService.getAll();
  }
  
}