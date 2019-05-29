package unitbv.cantinaApp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import unitbv.cantinaApp.payload.ApiResponse;
import unitbv.cantinaApp.payload.incident.NewIncidentRepresentation;
import unitbv.cantinaApp.service.IncidentService;
import unitbv.cantinaApp.service.InvoiceService;
import unitbv.cantinaApp.service.UserService;

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
	
	
}
