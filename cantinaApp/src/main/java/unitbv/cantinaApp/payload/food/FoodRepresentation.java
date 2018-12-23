package unitbv.cantinaApp.payload.food;

import java.math.BigDecimal;

public class FoodRepresentation {
	private String name;
	private BigDecimal price;
	private Integer weight;
	private Long id;
	
	public FoodRepresentation() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
