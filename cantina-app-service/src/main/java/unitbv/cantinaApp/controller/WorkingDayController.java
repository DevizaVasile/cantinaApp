package unitbv.cantinaApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import unitbv.cantinaApp.payload.ApiResponse;
import unitbv.cantinaApp.repository.entity.WorkingDay;
import unitbv.cantinaApp.service.WorkingDayService;

@RestController
@RequestMapping("/api/workingDay")
public class WorkingDayController {

	@Autowired
	WorkingDayService workingDayService;
	
	@PostMapping("/create/{day}")
	public ResponseEntity<?> createNewWorkingDay(@PathVariable("day") String day) {
		java.time.LocalDate date = java.time.LocalDate.parse(day);
		if(workingDayService.alreadyExist(date)) {
			return new ResponseEntity<>(new ApiResponse(false, "Woring day already exist"),HttpStatus.CONFLICT);
		}
		else {
			workingDayService.createNewWorkingDay(date);
			return new ResponseEntity<>(new ApiResponse(true, "New working day added"),HttpStatus.CREATED);
		}
		
	}
	
	@PostMapping("/toggleActive/{day}")
	public ResponseEntity<?> toggleActiveStatus(@PathVariable("day") String day) {
		java.time.LocalDate date = java.time.LocalDate.parse(day);
		if(workingDayService.alreadyExist(date)) {
			workingDayService.setWorkingDayActive(day);
			return new ResponseEntity<>(new ApiResponse(true, "Done"),HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<>(new ApiResponse(false, "Woring day does not exist"),HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/getFutureWorkingDays")
	public List<WorkingDay> getWorkigDays(){
		return  workingDayService.getAllFutureWorkingDays();
	}
	
}
