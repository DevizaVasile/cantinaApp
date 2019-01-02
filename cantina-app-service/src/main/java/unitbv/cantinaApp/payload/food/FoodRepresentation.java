package unitbv.cantinaApp.payload.food;

import java.math.BigDecimal;

public class FoodRepresentation {
	private String name;
	private BigDecimal price;
	private Integer weigth;
	private Long id;
	private boolean visible;
	
	public FoodRepresentation() {
		super();
	}
	
	public FoodRepresentation(String name, BigDecimal price, Integer weigth, Long id, boolean visible) {
		super();
		this.name = name;
		this.price = price;
		this.weigth = weigth;
		this.id = id;
		this.visible = visible;
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

	public Integer getWeigth() {
		return weigth;
	}

	public void setWeigth(Integer weight) {
		this.weigth = weight;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	
}
