package unitbv.cantinaApp.service;

import java.sql.Date;
import java.text.ParseException;
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
	
	public Invoice createNewInvoice(User user,String day) throws ParseException {
		if(!hasInvoiceForDay(user,day) ) {
			Invoice invoice = new Invoice();
			invoice.setUser(user);
			invoice.setDay(day.toString());
			invoice = invoiceRepository.save(invoice);
			user.addInvoice(invoice);
			userRepository.save(user);
			return invoice;
		}
		else {
			return invoiceRepository.findByUserAndDay(user, day).get();
		}
	}
	
	public List<InvoiceRepresentation> getAllFutureInvoices(User user) throws ParseException{
		List<Invoice> invoices =  invoiceRepository.findAllByUser(user); 
		List<InvoiceRepresentation> result = new ArrayList<InvoiceRepresentation>();
		Iterator<Invoice> itr = invoices.iterator();
		java.util.Date now = util.TimeUtils.fromInstantToDate();
		while(itr.hasNext()) {
			Invoice inv = itr.next();
			java.util.Date date = util.TimeUtils.fromStringToDate(inv.getDay());
			if(date.compareTo(now)>=0) {
				result.add(new InvoiceRepresentation(inv.getUser().getEmail(),inv.getDay()));
			}
		}
		return result;
	}
	
	public List<InvoiceRepresentation> getAllPastInvoices(User user) throws ParseException{
		List<Invoice> invoices =  invoiceRepository.findAllByUser(user); 
		List<InvoiceRepresentation> result = new ArrayList<InvoiceRepresentation>();
		Iterator<Invoice> itr = invoices.iterator();
		java.util.Date now = util.TimeUtils.fromInstantToDate();
		while(itr.hasNext()) {
			Invoice inv = itr.next();
			java.util.Date date = util.TimeUtils.fromStringToDate(inv.getDay());
			if(date.compareTo(now)<0) {
				result.add(new InvoiceRepresentation(inv.getUser().getEmail(),inv.getDay()));
			}
		}
		return result;
	}
	
	public List<InvoiceRepresentation> getAllInvoices(User user) throws ParseException{
		List<Invoice> invoices =  invoiceRepository.findAllByUser(user); 
		List<InvoiceRepresentation> result = new ArrayList<InvoiceRepresentation>();
		Iterator<Invoice> itr = invoices.iterator();
		while(itr.hasNext()) {
			Invoice inv = itr.next();
			result.add(new InvoiceRepresentation(inv.getUser().getEmail(),inv.getDay()));	
		}
		return result;
	}
	
	
	
	 
	private boolean hasInvoiceForDay(User user,String day) {
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
