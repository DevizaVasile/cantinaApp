package unitbv.cantinaApp.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unitbv.cantinaApp.repository.UserRepository;
import unitbv.cantinaApp.repository.entity.User;
import unitbv.cantinaApp.repository.enums.RoleName;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	

	
	public void createNewClient(String firstName, String lastName, String email) {
		User user = new User();
		user.setType(RoleName.ROLE_USER.toString());
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setBalance(new BigDecimal(0));
		user.setEmail(email);
		userRepository.save(user);
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
	
	
}
