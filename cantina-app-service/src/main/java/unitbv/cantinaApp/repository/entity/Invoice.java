package unitbv.cantinaApp.repository.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
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
	
	@Column(name="day")
	private String day;
	
	@Column(name="processed")
	private boolean processed;
	
	@OneToMany(mappedBy="invoice",  fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
	private List<InvoiceFood> invoiceFood;
	
	@ManyToOne( fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    @JoinColumn(name = "users_id")
    private User user;

	public Invoice() {
		super();
		this.createdAt = new Timestamp(System.currentTimeMillis());
		this.updatedAt = new Timestamp(System.currentTimeMillis());
	}

	public void addFood(Food food, Integer quantity) {
		if(this.invoiceFood == null) {
			this.invoiceFood = new ArrayList<InvoiceFood>();
		}
		
		if(hasFood(food)) {
			InvoiceFood invoiceFood = getInvoiceFoodByFoodId(food.getId());
			addQuantityToInvoice(invoiceFood,quantity);
		}
		else {
		InvoiceFood invoiceFoodAssociation = new InvoiceFood();
		invoiceFoodAssociation.setFood(food);
		invoiceFoodAssociation.setFoodId(food.getId());
		invoiceFoodAssociation.setInvoice(this);
		invoiceFoodAssociation.setInvoiceId(this.getId());
		invoiceFoodAssociation.setQuantity(quantity);
		this.invoiceFood.add(invoiceFoodAssociation);
		}	
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

	public List<InvoiceFood> getFood() {
		return invoiceFood;
	}

	public void setFood(List<InvoiceFood> food) {
		this.invoiceFood = food;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
	
	public boolean getProcessed() {
		return this.processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	private boolean hasFood(Food food) {
		boolean hasFood=false;
		if(this.invoiceFood != null) {
			Iterator<InvoiceFood> invoiceFoodIterator = this.invoiceFood.iterator();
			while(invoiceFoodIterator.hasNext()) {
				Long foodId = invoiceFoodIterator.next().getFoodId();
				Long newFoodId = food.getId();
				if(foodId.equals(newFoodId)) {
					hasFood =  true;
				}
			}
		}
		return hasFood;
	}
	
	private InvoiceFood getInvoiceFoodByFoodId(Long toFindFoodId) {
		InvoiceFood invoiceFoodToReturn = null;
		if(this.invoiceFood != null) {
			Iterator<InvoiceFood> invoiceFoodIterator = this.invoiceFood.iterator();
			while(invoiceFoodIterator.hasNext()) {
				InvoiceFood invoiceFood = invoiceFoodIterator.next();
				Long foodId = invoiceFood.getFoodId();
				if(foodId.equals(toFindFoodId)) {
					invoiceFoodToReturn = invoiceFood;
				}
			}
		}
		return invoiceFoodToReturn;
	}
	
	private void addQuantityToInvoice(InvoiceFood invoiceFood , Integer quantity) {
		invoiceFood.addQuantity(quantity);
	}
	
	public InvoiceFood removeFoodFromInvoce(Food food, Integer quantity) {
		InvoiceFood invoiceFood = getInvoiceFoodByFoodId(food.getId());
		invoiceFood.substractQuantity(quantity);
		return invoiceFood;
	}
	
	
	

}