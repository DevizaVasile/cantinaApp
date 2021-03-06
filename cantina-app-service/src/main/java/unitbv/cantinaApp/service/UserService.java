package unitbv.cantinaApp.service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unitbv.cantinaApp.payload.user.UserProfile;
import unitbv.cantinaApp.repository.RoleRepository;
import unitbv.cantinaApp.repository.UserRepository;
import unitbv.cantinaApp.repository.entity.Role;
import unitbv.cantinaApp.repository.entity.User;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	public void createNewClient(String firstName, String lastName, String email) {
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setBalance(new BigDecimal(0));
		user.setEmail(email);
		userRepository.save(user);
	}
	
	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	private boolean emailExists(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		return optionalUser.isPresent();
	}
	
	private boolean isValidEmail(String email) {
		
		final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		return VALID_EMAIL_ADDRESS_REGEX.matcher(email).matches();
	}
	
	public String registerIfPosible(String firstName, String lastName, String email) {
		
		String status="INTERNAL SERVER ERROR";
		
		try {
			if(emailExists(email)) {
				status="Adresa de email este deja folosita!";
			}
			else if(!isValidEmail(email)) {
				status="Adresa de email este invalida!";
			}
			else{
				createNewClient(firstName, lastName, email);
				status="Utilizatorul a fost inregistrat cu succes!";
			}
		}
		catch (Exception e) {
			 status="INTERNAL SERVER ERROR";
		}
		
		return status;
	}
	
	public boolean addRole(String email,String roleName) {
		Optional<User> userOptional = userRepository.findByEmail(email);
		Optional<Role> role = roleRepository.findByName(roleName);
		if(userOptional.isPresent() && role.isPresent()) {
			User user = userOptional.get();
			user.addRole(role.get());
			userRepository.save(user);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean removeRole(String email,String roleName) {
		Optional<User> userOptional = userRepository.findByEmail(email);
		Optional<Role> role = roleRepository.findByName(roleName);
		if(userOptional.isPresent() && role.isPresent()) {
			User user = userOptional.get();
			user.removeRole(role.get());
			userRepository.save(user);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean hasEnoughtMoney(User user,BigDecimal qunatity) {		
		BigDecimal result = user.getBalance().subtract(qunatity);
		BigDecimal zero = BigDecimal.valueOf(0);
		if( result.compareTo(zero) >= 0 ) {
			return true;
		}
		else {
			return false;
		}
	}

	public BigDecimal substractMoney(User user, BigDecimal qunatity) {
		BigDecimal result = user.getBalance().subtract(qunatity);
		user.setBalance(result);
		userRepository.save(user);
		return result;
	}
	
	public BigDecimal addMoney(User user, BigDecimal qunatity) {
		BigDecimal result = user.getBalance().add(qunatity);
		user.setBalance(result);
		userRepository.save(user);
		return result;
	}
	
	public UserProfile getUserProfile(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if(user.isPresent()) {
			return new UserProfile(user.get().getFirstName(),user.get().getLastName(),user.get().getBalance());
		}
		return null;
	}
	
	
}
