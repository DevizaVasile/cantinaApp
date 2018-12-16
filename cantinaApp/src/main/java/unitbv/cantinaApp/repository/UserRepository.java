package unitbv.cantinaApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import unitbv.cantinaApp.repository.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
	
	public  Optional<User> findByEmail(String email);

	public boolean existsByEmail(String email);
}
