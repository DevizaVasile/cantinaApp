package unitbv.cantinaApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import unitbv.cantinaApp.repository.entity.WorkingDay;

public interface WorkingDayRepository extends JpaRepository<WorkingDay,Long> {
	public Optional<WorkingDay> findByDay(java.time.LocalDate day);
	
	public List<WorkingDay> findByDayGreaterThan(java.time.LocalDate day);
}
