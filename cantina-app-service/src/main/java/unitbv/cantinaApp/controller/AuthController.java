package unitbv.cantinaApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import unitbv.cantinaApp.payload.ApiResponse;
import unitbv.cantinaApp.payload.JwtAuthenticationResponse;
import unitbv.cantinaApp.payload.user.ChangeRoleRequest;
import unitbv.cantinaApp.payload.user.LoginRequest;
import unitbv.cantinaApp.payload.user.SignUpRequest;
import unitbv.cantinaApp.payload.user.UserProfile;
import unitbv.cantinaApp.repository.RoleRepository;
import unitbv.cantinaApp.repository.UserRepository;
import unitbv.cantinaApp.repository.entity.Role;
import unitbv.cantinaApp.repository.entity.User;
import unitbv.cantinaApp.security.JwtTokenProvider;
import unitbv.cantinaApp.service.UserService;

import javax.validation.Valid;

import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;
    
    @Autowired
	UserService userService;

    @Autowired
    RoleRepository roleRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;
    
    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;
    
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
        Collection<?> roles = authentication.getAuthorities();
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt,roles,loginRequest.getEmail(),jwtExpirationInMs));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    	
    	System.out.println(signUpRequest.getEmail().toString());
    	System.out.println(signUpRequest.getPassword().toString());

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<Role> roleList = new ArrayList<Role>();
        roleList.add(roleRepository.findByName("ROLE_USER").get());
        roleList.add(roleRepository.findByName("ROLE_ADMIN").get());
        roleList.add(roleRepository.findByName("ROLE_STAFF").get());
        user.setRoles(roleList);
        User result = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
    
    @PostMapping("/addRole")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addRole(@Valid @RequestBody ChangeRoleRequest changeRoleReuqest) throws ParseException {
    	boolean result = userService.addRole(changeRoleReuqest.getEmail(), changeRoleReuqest.getRole());
    	if (result==true){
    		return new ResponseEntity<>(new ApiResponse(result, "Role "+changeRoleReuqest.getRole() +" added for user: "+changeRoleReuqest.getEmail()), HttpStatus.ACCEPTED); 
    	}
    	else
    	{
    		return new ResponseEntity<>(new ApiResponse(result, "User does not exist or already have this role"), HttpStatus.METHOD_NOT_ALLOWED); 
    	}
    }
    
    @PostMapping("/removeRole")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> removeRole(@Valid @RequestBody ChangeRoleRequest changeRoleReuqest) throws ParseException {
    	boolean result = userService.removeRole(changeRoleReuqest.getEmail(), changeRoleReuqest.getRole());
    	if (result==true){
    		return new ResponseEntity<>(new ApiResponse(result, "Role "+changeRoleReuqest.getRole() +" removed for user: "+changeRoleReuqest.getEmail()), HttpStatus.ACCEPTED); 
    	}
    	else
    	{
    		return new ResponseEntity<>(new ApiResponse(result, "User does not exist or   have this role"), HttpStatus.METHOD_NOT_ALLOWED); 
    	}
    }
    
    @GetMapping("/profile/{email}")
    @PreAuthorize("hasRole('USER')")
    public UserProfile getUserProfile(@PathVariable("email") String email){
    	return userService.getUserProfile(email);
    }
}