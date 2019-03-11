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
	
	
	public List<Menu> getMenu() {
		return menu;
	}

	public void setMenu(List<Menu> menu) {
		this.menu = menu;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Food other = (Food) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}