package unitbv.cantinaApp.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unitbv.cantinaApp.payload.invoice.InvoiceRepresentation;
import unitbv.cantinaApp.payload.order.OrderRepresentation;
import unitbv.cantinaApp.payload.order.OrderView;
import unitbv.cantinaApp.repository.FoodInvoiceRepository;
import unitbv.cantinaApp.repository.FoodRepository;
import unitbv.cantinaApp.repository.InvoiceRepository;
import unitbv.cantinaApp.repository.UserRepository;
import unitbv.cantinaApp.repository.entity.Food;
import unitbv.cantinaApp.repository.entity.Invoice;
import unitbv.cantinaApp.repository.entity.InvoiceFood;
import unitbv.cantinaApp.repository.entity.User;



@Service
public class InvoiceService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	InvoiceRepository invoiceRepository;
	
	@Autowired
	FoodRepository foodRepositlry;
	
	@Autowired
	FoodInvoiceRepository foodInvoiceRepository;
	
	public Invoice createNewInvoice(User user,String day, List<OrderRepresentation> order, BigDecimal sumRON) throws ParseException {
		Invoice returnInvoice;
		if(!hasInvoiceForDay(user,day) ) {
			Invoice invoice = new Invoice();
			invoice.setUser(user);
			invoice.setDay(day.toString());
			invoice.setProcessed(false);
			invoice = invoiceRepository.save(invoice);
			user.addInvoice(invoice);
			userRepository.save(user);
			returnInvoice =  invoice;
		}
		else {
			returnInvoice =  invoiceRepository.findByUserAndDay(user, day).get();
		}
		Iterator<OrderRepresentation> orderRepresentationIterator = order.iterator();
		while(orderRepresentationIterator.hasNext()) {
			OrderRepresentation orderRep = orderRepresentationIterator.next();
			orderRep.setOrderId(returnInvoice.getId());
		}
		this.addFoodToInvoice(returnInvoice, order);
		user.setBalance(user.getBalance().subtract(sumRON));
		return invoiceRepository.save(returnInvoice);
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
	
	public Invoice getInvoiceById(Long invoiceId) {
		return invoiceRepository.findById(invoiceId).get();
	}
	
	public void addFoodToInvoice(Invoice invoice, List<OrderRepresentation>orderRepresentationList) {
		Iterator<OrderRepresentation> orderRepresentationListIterator = orderRepresentationList.iterator();
		while(orderRepresentationListIterator.hasNext()) {
			OrderRepresentation orderRepresentation = orderRepresentationListIterator.next();
			Optional<Food> optionalFood = foodRepositlry.findById(orderRepresentation.getFoodId());
			if(optionalFood.isPresent()) {
				Food food = optionalFood.get();
				invoice.addFood(food, orderRepresentation.getQuantity());
			}
			invoiceRepository.save(invoice);
		}
	}
	
	public void removeFoodFromInvoice(Invoice invoice, List<OrderRepresentation>orderRepresentationList) {
		List<InvoiceFood> invoiceFoodToRemove = new ArrayList<InvoiceFood>();
		Iterator<OrderRepresentation> orderRepresentationListIterator = orderRepresentationList.iterator();
		while(orderRepresentationListIterator.hasNext()) {
			OrderRepresentation orderRepresentation = orderRepresentationListIterator.next();
			Optional<Food> optionalFood = foodRepositlry.findById(orderRepresentation.getFoodId());
			if(optionalFood.isPresent()) {
				Food food = optionalFood.get();
				InvoiceFood invoiceFood = invoice.removeFoodFromInvoce(food, orderRepresentation.getQuantity());
				if(invoiceFood.getQuantity() <= 0) {
					invoiceFoodToRemove.add(invoiceFood);
				}
			}
			invoice = invoiceRepository.save(invoice);
		}
		
		removeEmptyInvoiceFood(invoiceFoodToRemove);
	}
	
	private void removeEmptyInvoiceFood(List<InvoiceFood> toRemove) {
		Iterator<InvoiceFood> iterator = toRemove.iterator();
		while(iterator.hasNext()) {
			foodInvoiceRepository.delete(iterator.next());
		}
	}
	
	public List<OrderView> getOrderRepresentation(User user, String day){
		Optional<Invoice> optionalInvoice = invoiceRepository.findByUserAndDay(user, day);
		if(optionalInvoice.isPresent()) {
			Invoice invoice = optionalInvoice.get();
			List<InvoiceFood> invoiceFood = invoice.getFood();
			return this.getFoodRepresentationFromInvocieFood(invoiceFood);
		}
		return new ArrayList<OrderView>();
		
	}
	
	private List<OrderView> getFoodRepresentationFromInvocieFood(List<InvoiceFood> invoiceFood) {
		List<OrderView> orderView = new ArrayList<OrderView>();
		
		Iterator<InvoiceFood> invoiceFoodIterator =  invoiceFood.iterator();
		while(invoiceFoodIterator.hasNext()) {
			InvoiceFood invoiceFoodElement = invoiceFoodIterator.next();	
			orderView.add(new OrderView(invoiceFoodElement.getQuantity(),invoiceFoodElement.getFood().getName()));
		}
		return orderView;
	}
	
	public Long getInvoiceIdByUserIdAndDay(Long userId, String day) {
		User user = userRepository.findById(userId).get();
		if(userRepository.findById(userId).isPresent()) {
			if(invoiceRepository.findByUserAndDay(user, day).isPresent()) {
				Invoice invoice = invoiceRepository.findByUserAndDay(user, day).get();
				return invoice.getId(); 
			}
			else {
				return (long) 0;
			}
			
		}
		else {
			return (long) 0;
		}
	}
	
	public boolean isInvoiceClosed(User user, String day) {
		return invoiceRepository.findByUserAndDay(user, day).get().getProcessed();
	}
	
	public void closeInvoice(User user, String day) {
		if(!this.isInvoiceClosed(user, day)) {
			Invoice invoice = invoiceRepository.findByUserAndDay(user, day).get();
			invoice.setProcessed(true);
			invoiceRepository.save(invoice);
		}
	}
	
	
	
	
}
