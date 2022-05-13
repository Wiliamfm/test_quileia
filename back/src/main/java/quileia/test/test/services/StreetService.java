package quileia.test.test.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quileia.test.test.pojos.StreetType;
import quileia.test.test.repositories.StreetRepository;

@Service
public class StreetService {

  private StreetRepository streetRepository;

  @Autowired
  public StreetService(StreetRepository streetRepository){
    this.streetRepository= streetRepository;
  }

  public List<StreetType> getAll(){
    return streetRepository.findAll();
  }

  public StreetType getByName(String name){
    Optional<StreetType> optional= streetRepository.findByType(name);
    return optional.isPresent() ? optional.get() : null;
  }

  public StreetType getById(long id){
    Optional<StreetType> optional= streetRepository.findById(id);
    return optional.isPresent() ? optional.get() : null;
  }
  
}
