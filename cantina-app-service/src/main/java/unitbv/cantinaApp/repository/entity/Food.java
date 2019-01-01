package unitbv.cantinaApp.repository.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the food database table.
 * 
 */
@Entity
@NamedQuery(name="Food.findAll", query="SELECT f FROM Food f")
public class Food {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;

	@Column
	private BigDecimal price;

	@Column
	private int weight;
	
	@Column
	private boolean active;
	
	@OneToMany(mappedBy="food", fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
	 private List<InvoiceFood> invoiceFood;
	
	@OneToMany(mappedBy="food", fetch = FetchType.LAZY, cascade=CascadeType.MERGE)
	private List<Menu> menu;
	
	public Food() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getWeight() {
		return this.weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public List<InvoiceFood> getInvoiceFood() {
		return invoiceFood;
	}

	public void setInvoiceFood(List<InvoiceFood> invoiceFood) {
		this.invoiceFood = invoiceFood;
	}

	public Long getId() {
		return id;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	
	
}