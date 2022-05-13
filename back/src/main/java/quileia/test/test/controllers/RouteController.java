package quileia.test.test.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import quileia.test.test.pojos.RouteType;
import quileia.test.test.pojos.StreetType;
import quileia.test.test.pojos.TransitRoute;
import quileia.test.test.services.RouteService;
import quileia.test.test.services.RouteTypeService;
import quileia.test.test.services.StreetService;

@RestController
@RequestMapping("/routes")
@CrossOrigin
public class RouteController {

  private RouteService routeService;
  private RouteTypeService routeTypeService;
  private StreetService streetService;

  @Autowired
  public RouteController (RouteService routeService, RouteTypeService routeTypeService, StreetService streetService){
    this.routeService= routeService;
    this.routeTypeService= routeTypeService;
    this.streetService= streetService;
  }

  @GetMapping
  public List<TransitRoute> getAll(){
    return routeService.getAll();
  }

  @PostMapping TransitRoute create(@RequestBody HashMap<String, String> data){
    try {
      RouteType routeType= routeTypeService.getByName(data.get("route_type"));
      if(routeType != null){
        StreetType streetType= streetService.getByName(data.get("street_type"));
        if(streetType != null){
          if(data.get("id") != null){
            return routeService.update(Long.parseLong(data.get("id")) ,routeType, streetType, Integer.parseInt(data.get("number")), Double.parseDouble(data.get("contingency_level")));
          }else{
            return routeService.create(routeType, streetType, Integer.parseInt(data.get("number")), Double.parseDouble(data.get("contingency_level")));
          }
        }else{
          System.err.println(String.format("Street [%s] not found", data.get("street_type")));
          return null;
        }
      }else{
        System.err.println(String.format("Route type [%s] not found", data.get("route_type")));
        return null;
      }
    } catch (Exception e) {
      System.err.println(String.format("Error saving rout [%s] on post endpoint with error [%s]", data.get("number"), e.getMessage()));
      return null;
    }
  }

  @PostMapping("/{id}")
  public TransitRoute delete(@PathVariable("id") String id){
    return routeService.delete(Long.parseLong(id));
  }

  @PostMapping("/massiveLoad")
  public List<TransitRoute> createMassive(@RequestParam("routes") MultipartFile routeFile){
    try{
      System.out.println(routeFile.getOriginalFilename());
      System.out.println(String.format("FILE TYPE: {%s}", routeFile.getContentType()));
      if(routeFile != null && routeFile.getContentType().equals("text/csv")){
        File rFile = new File(System.getProperty("java.io.tmpdir")+"/"+routeFile.getOriginalFilename());
        routeFile.transferTo(rFile);
        List<List<String>> records2 = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rFile))) {
          String line;
          while ((line = br.readLine()) != null) {
              String[] values = line.split(",");
              records2.add(Arrays.asList(values));
            }
        }
        for (List<String> list : records2) {
          System.out.println(list);
          if(list.size() == 4){
            RouteType routeType= routeTypeService.getById(Long.parseLong(list.get(0)));
            StreetType streetType= streetService.getById(Long.parseLong(list.get(1)));
            if(routeType != null && streetType != null){
              routeService.create(routeType, streetType, Integer.parseInt(list.get(2)), Double.parseDouble(list.get(3)));
            }else{
              System.out.println("Route or street do not found on file");
            }
          }else{
            System.out.println(list);
          }
        }
      }
    }catch(IOException ioe){
      System.err.println(String.format("Error managingn files {%s}", ioe.getMessage()));
    }
    return null;
  }
  
}
