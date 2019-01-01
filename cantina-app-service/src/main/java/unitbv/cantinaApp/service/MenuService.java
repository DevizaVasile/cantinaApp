package unitbv.cantinaApp.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unitbv.cantinaApp.payload.food.FoodRepresentation;
import unitbv.cantinaApp.repository.FoodRepository;
import unitbv.cantinaApp.repository.MenuRepository;
import unitbv.cantinaApp.repository.entity.Food;
import unitbv.cantinaApp.repository.entity.Menu;

@Service
public class MenuService {

	@Autowired
	MenuRepository menuRepository;
	
	@Autowired
	FoodRepository foodRepository;
	
	public List<FoodRepresentation> getMenuForDate(String date){
		List<FoodRepresentation> menuForTheDate=new ArrayList<FoodRepresentation>();
		Iterator<Menu> menuIterator =  menuRepository.findAllByDate(date).iterator();
		while(menuIterator.hasNext()) {
			Menu menu = menuIterator.next();
			menuForTheDate.add(new FoodRepresentation(menu.getFood().getName(),
					menu.getFood().getPrice(),menu.getFood().getWeight(),
					menu.getFood().getId(),menu.getFood().getActive()));
		}
		return menuForTheDate;
	}
	
	public boolean createNewMenuEntry(String date, Long foodId) {
		Menu menu = new Menu();
		
		Optional<Food> food = foodRepository.findById(foodId);
		if(food.isPresent()) {
			List<Menu> menuToCheck =  menuRepository.findByFoodAndDate(food.get(), date);
			if(menuToCheck.isEmpty()) {
				menu.setDate(date);
				menu.setFood(food.get());
				menuRepository.save(menu);
				return true;
			}		
			return false;
		}
		else {
			return false;
			}
	}
}
