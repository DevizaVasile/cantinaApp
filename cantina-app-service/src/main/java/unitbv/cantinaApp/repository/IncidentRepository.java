package unitbv.cantinaApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import unitbv.cantinaApp.repository.entity.Incident;

public interface IncidentRepository extends JpaRepository<Incident,Long>  {
	List<Incident> findAllByUserId(Long userId);
}

