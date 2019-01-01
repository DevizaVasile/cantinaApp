package unitbv.cantinaApp.payload.menu;

import java.util.List;

import unitbv.cantinaApp.payload.food.FoodRepresentation;

public class MenuRepresentation {
	List<FoodRepresentation> food;
	String day;
	
	public MenuRepresentation() {
		super();
	}
	
	public List<FoodRepresentation> getFood() {
		return food;
	}
	public void setFood(List<FoodRepresentation> food) {
		this.food = food;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
}
