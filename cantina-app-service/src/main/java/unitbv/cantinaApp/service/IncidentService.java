package unitbv.cantinaApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unitbv.cantinaApp.repository.IncidentRepository;
import unitbv.cantinaApp.repository.UserRepository;
import unitbv.cantinaApp.repository.entity.Incident;

@Service
public class IncidentService {
	
	@Autowired
	IncidentRepository incidentRepository;
	
	@Autowired
	UserRepository userRepository;
	

	public Incident createNewIncident(Long id, String text, String day, String createdAt, String email, boolean isGeneric) {
		Incident incident = new Incident(id, text, isGeneric, day, createdAt, this.getUserId(email));
		incidentRepository.save(incident);
		return incident;
	}
	
	public List<Incident> getIncidentsByUser(Long userId){
		return incidentRepository.findAllByUserId(userId);
	}
	
	private Long getUserId(String email) {
		return userRepository.findByEmail(email).get().getId();
	}
}
