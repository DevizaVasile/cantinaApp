package unitbv.cantinaApp.controller;

import java.sql.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import unitbv.cantinaApp.payload.ApiResponse;
import unitbv.cantinaApp.payload.invoice.NewInvoiceRequest;
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
	private ResponseEntity<?> createNewInvoice(@Valid @RequestBody NewInvoiceRequest newInvoiceRequest) {
		String result = invoiceService.createNewInvoice(userService.getUserByEmail(newInvoiceRequest.getEmail()),Date.valueOf(newInvoiceRequest.getDay()));
		return new ResponseEntity<>(new ApiResponse(true, result),
                    HttpStatus.ACCEPTED);
	}
}
