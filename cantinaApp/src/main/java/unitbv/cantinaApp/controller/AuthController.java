package unitbv.cantinaApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import unitbv.cantinaApp.exception.AppException;
import unitbv.cantinaApp.payload.ApiResponse;
import unitbv.cantinaApp.payload.JwtAuthenticationResponse;
import unitbv.cantinaApp.payload.LoginRequest;
import unitbv.cantinaApp.payload.SignUpRequest;
import unitbv.cantinaApp.repository.FoodRepository;
import unitbv.cantinaApp.repository.InvoiceRepository;
import unitbv.cantinaApp.repository.RoleRepository;
import unitbv.cantinaApp.repository.UserRepository;
import unitbv.cantinaApp.repository.entity.Food;
import unitbv.cantinaApp.repository.entity.Invoice;
import unitbv.cantinaApp.repository.entity.InvoiceFood;
import unitbv.cantinaApp.repository.entity.InvoiceFoodId;
import unitbv.cantinaApp.repository.entity.Role;
import unitbv.cantinaApp.repository.entity.User;
import unitbv.cantinaApp.repository.enums.RoleName;
import unitbv.cantinaApp.security.JwtTokenProvider;
import unitbv.cantinaApp.service.FoodService;

import javax.persistence.EntityManager;
import javax.sound.midi.Soundbank;
import javax.validation.Valid;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    
    @Autowired  
    FoodRepository foodRepo;
    
    @Autowired
    InvoiceRepository invoiceRepo;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;
    
    //TODO remove after tests 
    @Autowired
    FoodService foodService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

       // Optional<User> userRole = roleRepository.findByName("ROLE_USER");
    
    //    roles.add(roleRepository.findAll());
        user.setRoles(roleRepository.findAll());

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
    
    @GetMapping("/signup")
    public void createNewUser() {
    	
//    	***********TEST REALM**********************
    	

    	//create save food
		Food food = new Food();
		food.setName("Food1");
		food.setPrice(BigDecimal.valueOf(10));
		food.setWeight(120);
		foodRepo.save(food);
		
		//get food
		Food foodItr = foodRepo.findAll().get(0);
		System.out.println(foodItr.getName());
    	
		//create save invoice
    	Invoice invoice = new Invoice();
    	invoiceRepo.save(invoice);
    	
    	//get invoice
    	invoice = invoiceRepo.findAll().get(0);
    	System.out.println(invoice.getId());
    	
    	invoice.addFood(foodItr, 4);
    	
    	invoiceRepo.save(invoice);
    
    	System.out.println("saved");
    	
    	Iterable<User> users = userRepository.findAll();
    	Iterator<User> itr = users.iterator();
    	User u = itr.next();
    	u.addInvoice(invoice);
    	
    	userRepository.save(u);
    	
    	
//    	***********TEST REALM**********************
//    	Iterable<User> users = userRepository.findAll();
//    	Iterator<User> itr = users.iterator();
//    	User u = itr.next();
//    	Role r1 = new Role();
//    	r1.setName(RoleName.ROLE_STAFF);
//    	r1.setId((long) 2);
//    	u.getRoles().add(r1);
//    	userRepository.save(u);
//    	Optional<User> u2 = userRepository.findById( (long) 1);
//    	System.out.println(u2.get().getRoles().toString());
//    	Set<Role> roles = u2.get().getRoles();
//    	Iterator<Role> rolesItr =  roles.iterator();
//    	while(rolesItr.hasNext()) {
//    		System.out.println(rolesItr.next().getName());
//    	}
//    	
//    	foodService.createNewFood("Shawrma", 300, 10.0);
//    	foodService.remove(1);
 
    }
}