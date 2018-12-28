package unitbv.cantinaApp.payload.food;

import javax.validation.constraints.NotBlank;

public class NewFoodRequest {

	@NotBlank
    private String name;
	
	private int weigth;
	
	private Double price;
	
	private boolean active;
	
	public NewFoodRequest() {
		super();
	}
	
	public NewFoodRequest(@NotBlank String name, int weigth, Double price,boolean active) {
		super();
		this.name = name;
		this.weigth = weigth;
		this.price = price;
		this.active  = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeigth() {
		return weigth;
	}

	public void setWeigth(int weigth) {
		this.weigth = weigth;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
