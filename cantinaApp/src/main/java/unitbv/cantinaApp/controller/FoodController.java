package unitbv.cantinaApp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import unitbv.cantinaApp.payload.ApiResponse;
import unitbv.cantinaApp.payload.food.FoodRepresentation;
import unitbv.cantinaApp.payload.food.NewFoodRequest;
import unitbv.cantinaApp.repository.entity.Food;
import unitbv.cantinaApp.service.FoodService;

@RestController
@RequestMapping("/api/food")
public class FoodController {
	
	@Autowired
    FoodService foodService;
	
    @GetMapping("/getAll")
    @PreAuthorize("hasRole('USER')")
    public List<FoodRepresentation> getAll(){
    	return foodService.getAll();
    }
    
    @PostMapping("/create")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> createNew(@Valid @RequestBody NewFoodRequest newFoodRequest){
    	Food food =  foodService.findByName(newFoodRequest.getName());
    	if(food == null) {
    		foodService.createNewFood(newFoodRequest.getName(), newFoodRequest.getWeigth(), newFoodRequest.getPrice(), newFoodRequest.getActive());
    		return new ResponseEntity<>(new ApiResponse(true, "New food has been added"),HttpStatus.CREATED);
    	}
    	else
    	{
    		return new ResponseEntity<>(new ApiResponse(true, "Already exist"),HttpStatus.ALREADY_REPORTED);
    	}
    }
    
    @GetMapping("/test")
    @PreAuthorize("hasRole('ADMIN')")
    public void test() {

//    	foodService.createNewFood("Sarmale",130,10.5);
//    	foodService.createNewFood("Ciorba de burta",300,12.0);
//    	foodService.createNewFood("Cascaval pane",200,4.0);
//    	
//    	System.out.println("Food Created");
//    	
//    	Food f = foodService.findByName("Sarmale");
//    	System.out.println("Print Sarmale from Database");
//    	System.out.println(f.getName()+ " "+f.getWeight()+" "+f.getPrice());
//    	
//    	System.out.println("Change sarmale data to Sarmale2 ,240 , 20.30");
//    	f.setName("Sarmale2");
//    	f.setPrice(BigDecimal.valueOf(20.30));
//    	f.setWeight(240);
//    	
//    	System.out.println("Save new food to Database");
//    	foodService.update(f);
//    	
//    	f = foodService.findByName("Sarmale2");
//    	System.out.println("Print new food from database");
//    	System.out.println(f.getName()+ " "+f.getWeight()+" "+f.getPrice());
//    	
//    	foodService.remove(f);
    	
//    	User user  = userRepository.findById(Long.valueOf(1)).get();
//    	
//		List<Food> allFood = foodRepository.findAll();
//		Invoice invoice = new Invoice();
//		invoice.setUser(user);
//		
//		InvoiceFood invoiceFood = new InvoiceFood();
//		invoiceFood.setFood(allFood.get(0));
//		invoiceFood.setInvoice(invoice);
//		invoiceFood.setQuantity(2);
//		invoiceFood.setInvoiceDate(java.sql.Date.from(Instant.now()));
//		
//		invoiceRepository.save(invoice);
//		invoiceFoodRepository.save(invoiceFood);
		
		
    	
    	
    }
}
