package unitbv.cantinaApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import unitbv.cantinaApp.repository.entity.Role;

public interface RoleRepository  extends JpaRepository<Role,Long> {

}
