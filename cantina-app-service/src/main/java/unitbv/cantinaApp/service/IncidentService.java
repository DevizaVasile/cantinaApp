package unitbv.cantinaApp.service;

import java.util.List;
import java.util.Optional;
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
		Optional<Incident> optIncident = this.getIncidentForTheDay(this.getUserId(email), day);
		if(optIncident.isPresent()) {
			return optIncident.get();
		}
		else {
			Incident incident = new Incident(id, text, isGeneric, day, createdAt, this.getUserId(email));
			incidentRepository.save(incident);
			return incident;
		}
	}
	
	public List<Incident> getIncidentsByUser(Long userId){
		return incidentRepository.findAllByUserId(userId);
	}
	
	public Long getUserId(String email) {
		return userRepository.findByEmail(email).get().getId();
	}
	
	public Optional<Incident> getIncidentForTheDay(Long userId, String day){
		 Optional<Incident> incidentOptional = incidentRepository.findByUserIdAndDay(userId, day);
		 return incidentOptional;
	}
	
	public boolean setIncidentStatus(Long userId, String day, int status) {
		Optional<Incident> incidentOpt = this.getIncidentForTheDay(userId, day);
		if(incidentOpt.isPresent()) {
			Incident incident = incidentOpt.get();
			incident.setStatus(status);
			incidentRepository.save(incident);
			return true;
		}
		return false;
	}
	
	public boolean setIncidentResponse(Long userId, String day, String response) {
		Optional<Incident> incidentOpt = this.getIncidentForTheDay(userId, day);
		if(incidentOpt.isPresent()) {
			Incident incident = incidentOpt.get();
			incident.setResponse(response);
			incident.setStatus(1);
			incidentRepository.save(incident);
			return true;
		}
		return false;
	}
	
	public List<Incident> getAllOpenIncidents(){
		List<Incident> incidents = incidentRepository.findAllByStatus(0);
		return incidents;
	}
	
	public int getIncidentStatusByUserIdAndDay(Long userId, String day) {
		Optional<Incident> incident =  incidentRepository.findByUserIdAndDay(userId, day);
		if(incident.isPresent()) {
			return incident.get().getStatus();
		}
		else {
			return -1;
		}
	}
	
	public String getIncidentMessage(String email, String day) {
		return incidentRepository.findByUserIdAndDay(this.getUserId(email), day).get().getResponse();
	}
	
}
