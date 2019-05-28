package unitbv.cantinaApp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import unitbv.cantinaApp.payload.ApiResponse;
import unitbv.cantinaApp.payload.incident.NewIncidentRepresentation;
import unitbv.cantinaApp.service.IncidentService;

@RestController
@RequestMapping("/api/incident")
public class IncidentController {
	
	@Autowired
	IncidentService incidentService;
	
	@PostMapping("/create")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> createNewIncident(@Valid @RequestBody NewIncidentRepresentation newIncidentRepresentation){
		System.out.println("GG");
		incidentService.createNewIncident(newIncidentRepresentation.getId(), newIncidentRepresentation.getText(), newIncidentRepresentation.getDay(), 
				newIncidentRepresentation.getCreatedAt(), newIncidentRepresentation.getUserId(), newIncidentRepresentation.isGeneric());
		return new ResponseEntity<>(new ApiResponse(true, "New incident has been added"),HttpStatus.CREATED);
	}
	
	@GetMapping("/test")
	public String hi() {
		return "HI!";
	}
}
