package unitbv.cantinaApp.repository.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the invoice database table.
 * 
 */
@Entity
@NamedQuery(name="Invoice.findAll", query="SELECT i FROM Invoice i")
public class Invoice {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="created_at")
	private Timestamp createdAt;

	@Column(name="updated_at")
	private Timestamp updatedAt;
	
	@OneToMany(mappedBy="invoice",  fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
	 private List<InvoiceFood> food;
	
	@ManyToOne( fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    @JoinColumn(name = "users_id")
    private User user;

	public void addFood(Food food, Integer quantity) {
		InvoiceFood invoiceFoodAssociation = new InvoiceFood();
		invoiceFoodAssociation.setFood(food);
		invoiceFoodAssociation.setFoodId(food.getId());
		invoiceFoodAssociation.setInvoice(this);
		invoiceFoodAssociation.setInvoiceId(this.getId());
		invoiceFoodAssociation.setQuantity(quantity);
		
		if(this.food == null) {
			this.food = new ArrayList<InvoiceFood>();
		}
		this.food.add(invoiceFoodAssociation);
	}

	public List<InvoiceFood> getInvoiceFood() {
		return food;
	}

	public void setInvoiceFood(List<InvoiceFood> invoiceFood) {
		this.food = invoiceFood;
	}

	public Long getId() {
		return id;
	}

	public List<InvoiceFood> getFood() {
		return food;
	}

	public void setFood(List<InvoiceFood> food) {
		this.food = food;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}	
	
	

}