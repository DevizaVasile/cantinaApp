package unitbv.cantinaApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import unitbv.cantinaApp.repository.entity.Incident;

public interface IncidentRepository extends JpaRepository<Incident,Long>  {
	List<Incident> findAllByUserId(Long userId);
	List<Incident> findAllByUserIdAndStatus(Long userId, int status);
	Optional<Incident> findByUserIdAndDay(Long userId, String day);
	List<Incident> findAllByStatus(int status);
}

