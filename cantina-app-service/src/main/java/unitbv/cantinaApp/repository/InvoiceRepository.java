package unitbv.cantinaApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import unitbv.cantinaApp.repository.entity.Invoice;
import unitbv.cantinaApp.repository.entity.User;

public interface InvoiceRepository extends JpaRepository<Invoice,Long>  {

	public Optional<Invoice> findByUserAndDay(User user, String day);
	
	public List<Invoice> findAllByUser(User user);
}
