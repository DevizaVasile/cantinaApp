package unitbv.cantinaApp.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import unitbv.cantinaApp.payload.ApiResponse;
import unitbv.cantinaApp.payload.invoice.InvoiceRepresentation;
import unitbv.cantinaApp.payload.invoice.InvoicesRequest;
import unitbv.cantinaApp.payload.invoice.NewInvoiceRequest;
import unitbv.cantinaApp.payload.order.OrderRepresentation;
import unitbv.cantinaApp.repository.entity.Invoice;
import unitbv.cantinaApp.repository.entity.User;
import unitbv.cantinaApp.service.InvoiceService;
import unitbv.cantinaApp.service.UserService;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

	@Autowired
	InvoiceService invoiceService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/newInvoice")
	private ResponseEntity<?> createNewInvoice(@Valid @RequestBody NewInvoiceRequest newInvoiceRequest) throws ParseException {
		java.util.Date day = util.TimeUtils.fromStringToDate(newInvoiceRequest.getDay());
		if(invoiceService.isValidDay(util.TimeUtils.fromUtilDateToSqlDate(day))) {
			invoiceService.createNewInvoice(userService.getUserByEmail(newInvoiceRequest.getEmail()),newInvoiceRequest.getDay(),  
					newInvoiceRequest.getOrder(), BigDecimal.valueOf(newInvoiceRequest.getSumRON()));			
			return new ResponseEntity<>(new ApiResponse(true, "Invoice has been created or updated"),HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<>(new ApiResponse(true, "Selected day is not valid"),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/getFutureInvoices")
	private List<InvoiceRepresentation> getAllFutureInvoices(@RequestBody InvoicesRequest request) throws ParseException{
		User user = userService.getUserByEmail(request.getEmail());
		return invoiceService.getAllFutureInvoices(user);
	}
	
	@PostMapping("/getPastInvoices")
	private List<InvoiceRepresentation> getAllPastInvoices(@RequestBody InvoicesRequest request) throws ParseException{
		User user = userService.getUserByEmail(request.getEmail());
		return invoiceService.getAllPastInvoices(user);
	}
	
	@PostMapping("/getAllInvoices")
	private List<InvoiceRepresentation> getAllInvoices(@RequestBody InvoicesRequest request) throws ParseException{
		User user = userService.getUserByEmail(request.getEmail());
		return invoiceService.getAllInvoices(user);
	}
	
	@PostMapping("/addFood/{invoiceId}")
	private void addFoodToInvoice(@PathVariable("invoiceId") long invoiceId, @RequestBody List<OrderRepresentation> orderRepresentationList) {
		Invoice invoice = invoiceService.getInvoiceById(invoiceId);
		invoiceService.addFoodToInvoice(invoice, orderRepresentationList);
	}
	
	@PostMapping("/removeFood/{invoiceId}")
	private void removeFoodToInvoice(@PathVariable("invoiceId") long invoiceId, @RequestBody List<OrderRepresentation> orderRepresentationList) {
		Invoice invoice = invoiceService.getInvoiceById(invoiceId);
		invoiceService.removeFoodFromInvoice(invoice, orderRepresentationList);
	}
}
