package unitbv.cantinaApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import unitbv.cantinaApp.repository.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice,Long>  {

}
