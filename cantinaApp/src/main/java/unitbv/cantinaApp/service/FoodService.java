package unitbv.cantinaApp.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unitbv.cantinaApp.repository.FoodRepository;
import unitbv.cantinaApp.repository.entity.Food;

@Service
public class FoodService {
		
	@Autowired
	private FoodRepository foodRepository;
	
	public void createNewFood(String name, int weight, Double price)
	{
		Food food = new Food();
		food.setName(name);
		food.setWeight(weight);
		food.setPrice(BigDecimal.valueOf(price));
		foodRepository.save(food);
	}
	
	public Food findById(long id) {
		Optional<Food> optionalFood =  foodRepository.findById(id);
		Food food;
		if(optionalFood.isPresent()){
			 food = optionalFood.get();
		}
		else {
		food=null;
		}
		return food;
	}
	
	public Food findByName(String name) {
		Optional<Food> optionalFood =  foodRepository.findByName(name);
		Food food;
		if(optionalFood.isPresent()){
			 food = optionalFood.get();
		}
		else {
		food=null;
		}
		return food;
	}
	
	public void setName(long id, String name) {
		Food food = this.findById(id);
		if(food != null) {
			food.setName(name);
			foodRepository.save(food);
		}
	}
	
	public void setPrice(long id, double price) {
		Food food = this.findById(id);
		if(food != null) {
			food.setPrice(BigDecimal.valueOf(price));
			foodRepository.save(food);
		}
	}
	
	public void setWeight(long id, int weight) {
		Food food = this.findById(id);
		if(food != null) {
			food.setWeight(weight);
			foodRepository.save(food);
		}
	}
	
	public void remove(long id) {
		Food food = this.findById(id);
		if(food != null) {
			foodRepository.delete(food);
		}
	}
	
	public void remove(Food food) {
		Optional<Food> optFood = foodRepository.findByName(food.getName());
		if (optFood.isPresent()) {
			Food f = optFood.get();
			foodRepository.delete(f);
		};
	}
	
	public List<Food> getAll(){
		List<Food> allFood = foodRepository.findAll();
		return allFood;
	}
	
	public void update(Food food) {
		foodRepository.save(food);
		
	}
	
}
