package us.odm.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class MainController {

  @Autowired
  SubscriptionService subService;
  
  @PostMapping("/sub")
  public String createNewSubscription(@RequestBody Subscription sub) {
    try {
      subService.createSub(sub);
      return "Subscription Created";
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
    }
  }

}
