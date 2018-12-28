package unitbv.cantinaApp.payload.order;

public class OrderRepresentation {
	Long foodId;
	Long orderId;
	Integer quantity;
	
	public OrderRepresentation() {
		super();
	}
	
	public OrderRepresentation(Long foodId, Long orderId, Integer quantity) {
		super();
		this.foodId = foodId;
		this.orderId = orderId;
		this.quantity = quantity;
	}

	public Long getFoodId() {
		return foodId;
	}
	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
