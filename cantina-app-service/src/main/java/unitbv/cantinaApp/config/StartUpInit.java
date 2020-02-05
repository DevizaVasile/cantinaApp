package unitbv.cantinaApp.config;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import unitbv.cantinaApp.repository.RoleRepository;
import unitbv.cantinaApp.repository.entity.Role;

@Component
public class StartUpInit {
  @Autowired
  RoleRepository roleRepository;
  @PostConstruct
  public void init(){
     // init code goes here
	  Optional<Role> opt = roleRepository.findByName("ROLE_USER");
	  if(!opt.isPresent()) {
		 Role r = new Role();
		 r.setName("ROLE_USER");
		 roleRepository.save(r);
	  }
	  opt = roleRepository.findByName("ROLE_ADMIN");
	  if(!opt.isPresent()) {
			 Role r = new Role();
			 r.setName("ROLE_ADMIN");
			 roleRepository.save(r);
		  }
	  opt = roleRepository.findByName("ROLE_STAFF");
	  if(!opt.isPresent()) {
			 Role r = new Role();
			 r.setName("ROLE_STAFF");
			 roleRepository.save(r);
		  }
  }
}
