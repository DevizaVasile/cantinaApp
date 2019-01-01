package unitbv.cantinaApp.payload.menu;

public class NewMenuEntry {
	String day;
	Long foodId;

	public NewMenuEntry() {
		super();
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public Long getFoodId() {
		return foodId;
	}
	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}
	
	
}
