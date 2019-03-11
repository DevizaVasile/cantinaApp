package unitbv.cantinaApp.payload.menu;

import java.util.List;

import unitbv.cantinaApp.payload.food.FoodRepresentation;

public class UpdateFoodRepresentation {

	List<FoodRepresentation> toAdd;
	List<FoodRepresentation> toRemove;
	
	public List<FoodRepresentation> getToAdd() {
		return toAdd;
	}
	public void setToAdd(List<FoodRepresentation> toAdd) {
		this.toAdd = toAdd;
	}
	public List<FoodRepresentation> getToRemove() {
		return toRemove;
	}
	public void setToRemove(List<FoodRepresentation> toRemove) {
		this.toRemove = toRemove;
	}
	
	
	
}
