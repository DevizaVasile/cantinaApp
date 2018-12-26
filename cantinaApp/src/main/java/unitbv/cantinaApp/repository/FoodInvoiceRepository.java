package unitbv.cantinaApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import unitbv.cantinaApp.repository.entity.InvoiceFood;
import unitbv.cantinaApp.repository.entity.InvoiceFoodId;

public interface FoodInvoiceRepository extends JpaRepository<InvoiceFood,InvoiceFoodId>   {
	
}
