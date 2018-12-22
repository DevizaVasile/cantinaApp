package unitbv.cantinaApp.service;

import java.sql.Date;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unitbv.cantinaApp.payload.invoice.InvoiceRepresentation;
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
	
	public Invoice createNewInvoice(User user,Date day) throws ParseException {
		if(!hasInvoiceForDay(user,day) ) {
			Invoice invoice = new Invoice();
			invoice.setUser(user);
			invoice.setDay(day);
			invoice = invoiceRepository.save(invoice);
			user.addInvoice(invoice);
			userRepository.save(user);
			return invoice;
		}
		else {
			return invoiceRepository.findByUserAndDay(user, day).get();
		}
	}
	
	public List<InvoiceRepresentation> getAllFutureInvoices(User user){
		java.util.Date date = java.util.Date.from(Instant.now());
		List<Invoice> invoices =  invoiceRepository.findByUserAndDayGreaterThan(user, util.TimeUtils.fromUtilDateToSqlDate(date)); 
		List<InvoiceRepresentation> result = new ArrayList<InvoiceRepresentation>();
		Iterator<Invoice> itr = invoices.iterator();
		while(itr.hasNext()) {
			Invoice inv = itr.next();
			result.add(new InvoiceRepresentation(inv.getUser().getEmail(),inv.getDay().toString()));
		}
		return result;
	}
	
	
	
	 
	private boolean hasInvoiceForDay(User user,Date day) {
		return invoiceRepository.findByUserAndDay(user, day).isPresent();
	}
	
	public boolean isValidDay(Date day) throws ParseException {
		boolean isValidday=true;
		java.util.Date javaDay = util.TimeUtils.fromStringToDate(day.toString());
		java.util.Date javaNow = util.TimeUtils.fromInstantToDate();
		if(javaDay.compareTo(javaNow)<=0) {
			isValidday=false;
			}
		if(javaDay.toString().startsWith("Sun") || javaDay.toString().startsWith("Sat")) {
			isValidday=false;
		}
		return isValidday;
		}
}
