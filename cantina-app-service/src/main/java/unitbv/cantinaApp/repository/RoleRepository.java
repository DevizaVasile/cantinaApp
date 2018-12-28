package unitbv.cantinaApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import unitbv.cantinaApp.repository.entity.Role;

public interface RoleRepository  extends JpaRepository<Role,Long> {
	
	public  Optional<Role> findByName(String name);
}
