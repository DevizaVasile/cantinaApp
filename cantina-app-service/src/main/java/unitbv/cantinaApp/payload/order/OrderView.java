package unitbv.cantinaApp.payload.order;

public class OrderView {
	Integer quantity;
	String foodName;
	
	public OrderView(Integer quantity, String foodName) {
		super();
		this.quantity = quantity;
		this.foodName = foodName;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

}
