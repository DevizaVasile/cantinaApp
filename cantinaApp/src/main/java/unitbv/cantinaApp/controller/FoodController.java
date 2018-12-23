package unitbv.cantinaApp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('USER')")
    public FoodRepresentation getFoodById(@PathVariable("id") long id) {
    	Food food = foodService.findById(id);
    	FoodRepresentation foodRepresentation = new FoodRepresentation();
    	foodRepresentation.setId(food.getId());
    	foodRepresentation.setName(food.getName());
    	foodRepresentation.setPrice(food.getPrice());
    	foodRepresentation.setVisible(food.getActive());
    	foodRepresentation.setWeight(food.getWeight());
    	return foodRepresentation;
    }
    
    @PostMapping("/set")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> setFoodById(@Valid @RequestBody FoodRepresentation foodRepresentation) {
    	boolean result =  foodService.update(foodRepresentation);
    	if(result==true) {
    		return new ResponseEntity<>(new ApiResponse(result, "Done"),HttpStatus.OK);
    	}
    	else {
    		return new ResponseEntity<>(new ApiResponse(result, "Internal error"),HttpStatus.BAD_REQUEST);
    	}

    }
    
    
}
