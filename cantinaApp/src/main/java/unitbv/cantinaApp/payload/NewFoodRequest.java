package unitbv.cantinaApp.payload;

import javax.validation.constraints.NotBlank;

public class NewFoodRequest {

	@NotBlank
    private String name;
	
	private int weigth;
	
	private Double price;
	
	public NewFoodRequest() {
		super();
	}
	
	public NewFoodRequest(@NotBlank String name, int weigth, Double price) {
		super();
		this.name = name;
		this.weigth = weigth;
		this.price = price;
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
	
	
	
}
