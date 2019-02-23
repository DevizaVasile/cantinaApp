package unitbv.cantinaApp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unitbv.cantinaApp.repository.WorkingDayRepository;
import unitbv.cantinaApp.repository.entity.WorkingDay;

@Service
public class WorkingDayService {
	
	@Autowired
	WorkingDayRepository workingDayRepository;
	
	public List<WorkingDay> getAllFutureWorkingDays() {
		LocalDate now = LocalDate.now();
		return workingDayRepository.findByDayGreaterThan(now);	
	}
	
	public void createNewWorkingDay(LocalDate day) {
		System.out.println(day.toString());
		WorkingDay workingDay = new WorkingDay();
		workingDay.setDay(day);
		workingDay.setVisible(Boolean.FALSE);
		workingDayRepository.save(workingDay);
	}
	
	public boolean setWorkingDayActive(String day)
	{
		java.time.LocalDate date = java.time.LocalDate.parse(day);
		Optional<WorkingDay> workingDayOptional = workingDayRepository.findByDay(date);
		if(workingDayOptional.isPresent()) {
			WorkingDay workingDay   = workingDayOptional.get();
			workingDay.setVisible(!workingDay.getVisible());
			workingDayRepository.save(workingDay);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean alreadyExist(java.time.LocalDate day) {
		return workingDayRepository.findByDay(day).isPresent();
	}
}
