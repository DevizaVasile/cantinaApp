package unitbv.cantinaApp.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unitbv.cantinaApp.repository.FoodRepository;
import unitbv.cantinaApp.repository.UserRepository;
import unitbv.cantinaApp.repository.entity.Food;
import unitbv.cantinaApp.repository.entity.Invoice;
import unitbv.cantinaApp.repository.entity.User;



@Service
public class InvoiceService {
	
	@Autowired
	UserRepository userRepository;
	
	public void createNewInvoice(User user) {
		
	
	}
}
