package quileia.test.test.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import quileia.test.test.pojos.Auditory;
import quileia.test.test.services.AuditoryService;

@RestController
@RequestMapping("/auditory")
@CrossOrigin
public class AuditoryController {

  private AuditoryService auditoryService;

  @Autowired
  public AuditoryController(AuditoryService auditoryService){
    this.auditoryService= auditoryService;
  }

  @GetMapping("/agents/{id}")
  public List<Auditory> getByAgent(@PathVariable("id") String id){
    return auditoryService.getByAgent(id);
  }

  @GetMapping("/routes/{id}")
  public List<Auditory> getByRoute(@PathVariable("id") long id){
    return auditoryService.getByRoute(id);
  }
  
}
