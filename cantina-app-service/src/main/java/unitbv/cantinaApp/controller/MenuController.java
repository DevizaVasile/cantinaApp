package unitbv.cantinaApp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import unitbv.cantinaApp.payload.ApiResponse;
import unitbv.cantinaApp.payload.food.FoodRepresentation;
import unitbv.cantinaApp.payload.menu.MenuRepresentation;
import unitbv.cantinaApp.payload.menu.NewMenuEntry;
import unitbv.cantinaApp.service.MenuService;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
	
	@Autowired
	MenuService menuService;
	
	
	@GetMapping("/getAll/{date}")
	public MenuRepresentation getMenuForTheDay(@PathVariable("date") String date) {
		System.out.println(date);
		MenuRepresentation menu = new MenuRepresentation();
		List<FoodRepresentation> foodForTheDay = menuService.getMenuForDate(date);
		menu.setFood(foodForTheDay);
		menu.setDay(date);
		return menu;
	}
	
	@PostMapping("addNew")
	public ResponseEntity<?> addNewFoodToMenu(@Valid @RequestBody NewMenuEntry menuEntry){
		boolean succes = menuService.createNewMenuEntry(menuEntry.getDay(), menuEntry.getFoodId());
		if(succes) {
			return new ResponseEntity<>(new ApiResponse(true, "New food has been added"),HttpStatus.CREATED);
		}
		else
		{
			return new ResponseEntity<>(new ApiResponse(false, "Something went wrong"),HttpStatus.BAD_REQUEST);
		}
	}
}
