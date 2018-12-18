package unitbv.cantinaApp.service;


import java.sql.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unitbv.cantinaApp.repository.InvoiceRepository;
import unitbv.cantinaApp.repository.UserRepository;
import unitbv.cantinaApp.repository.entity.Invoice;
import unitbv.cantinaApp.repository.entity.User;



@Service
public class InvoiceService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	InvoiceRepository invoiceRepository;
	
	public String createNewInvoice(User user,Date day) {
		String response;
		if(!hasInvoiceForDay(user,day)) {
			Invoice invoice = new Invoice();
			invoice.setUser(user);
			invoice.setDay(day);
			invoice.setUser(user);
			invoice = invoiceRepository.save(invoice);
			user.addInvoice(invoice);
			userRepository.save(user);
			response = "Order created";
		}
		else {
			response = "Order already exists";
		}
		return response;
	};
	
	private boolean hasInvoiceForDay(User user,Date day) {
		return invoiceRepository.findByUserAndDay(user, day).isPresent();
	}
}
