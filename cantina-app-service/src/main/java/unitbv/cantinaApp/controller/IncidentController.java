package unitbv.cantinaApp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import unitbv.cantinaApp.payload.ApiResponse;
import unitbv.cantinaApp.payload.incident.IncidentData;
import unitbv.cantinaApp.payload.incident.IncidentMessage;
import unitbv.cantinaApp.payload.incident.NewIncidentRepresentation;
import unitbv.cantinaApp.service.IncidentService;
import unitbv.cantinaApp.service.InvoiceService;
import unitbv.cantinaApp.service.UserService;
import unitbv.cantinaApp.repository.entity.Incident;;

@RestController
@RequestMapping("/api/incident")
public class IncidentController {
	
	@Autowired
	IncidentService incidentService;
	
	@Autowired
	InvoiceService invoiceService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/create")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> createNewIncident(@Valid @RequestBody NewIncidentRepresentation newIncidentRepresentation){
		boolean isGeneric;
		Long userId = userService.getUserByEmail(newIncidentRepresentation.getUserEmail()).get().getId();
		Long invoiceId = invoiceService.getInvoiceIdByUserIdAndDay(userId, newIncidentRepresentation.getDay());
		if(invoiceId.equals(Long.parseLong("0"))) {
			return new ResponseEntity<>(new ApiResponse(false, "Not found"),HttpStatus.BAD_REQUEST);
		}
		if(newIncidentRepresentation.getGeneric()==1) {
			isGeneric=true;
		}
		else {
			isGeneric=false;
		}
		incidentService.createNewIncident(invoiceId, newIncidentRepresentation.getText(), newIncidentRepresentation.getDay(),
				newIncidentRepresentation.getCreatedAt(), newIncidentRepresentation.getUserEmail(),isGeneric);
		return new ResponseEntity<>(new ApiResponse(true, "New incident has been added"),HttpStatus.CREATED);
	}
	
	@PostMapping("/setResponse")
	@PreAuthorize("hasRole('STAFF')")
	public boolean messageUserAboutIncident(@Valid @RequestBody IncidentMessage incidentMessage){
		incidentService.setIncidentResponse(incidentMessage.getUserId(), incidentMessage.getDay(), incidentMessage.getResponse());
		return true;
	}
	
	@GetMapping("/getAllOpen")
	@PreAuthorize("hasRole('STAFF')")
	public List<Incident> getAllOpenIncidents(){
		return incidentService.getAllOpenIncidents();
	}
	
	
	@PostMapping("/getMessageFor")
	public IncidentData getMessage(@Valid @RequestBody IncidentData incidentData){	
		 String message = incidentService.getIncidentMessage(incidentData.getUserId(), incidentData.getDay());
		 IncidentData response = new  IncidentData();
		 response.setResponse(message);
		 return response;
	}
	
	@RequestMapping(value = "/giveFeedback/{day}/{userId}/{statusValue}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER')")
	@ResponseBody
	public boolean giveFeedback(@PathVariable("day") String day, @PathVariable("userId") String userId, @PathVariable("statusValue") int statusValue){
		Long id = incidentService.getUserId(userId);
		return incidentService.setIncidentStatus(id, day, statusValue);
	}
	
	
}
