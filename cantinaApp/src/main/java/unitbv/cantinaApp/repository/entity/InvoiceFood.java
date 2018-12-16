package unitbv.cantinaApp.repository.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Invoice_Food")
@IdClass(InvoiceFoodId.class)
public class InvoiceFood {

	
	 @Id
	 private long foodId;
	 @Id
	 private long invoiceId;
	 
	 @Column(name="quantity")
	 private Integer quantity;	
	
	 @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
	 @JoinColumn(name = "invoiceId", updatable = false, insertable = false, referencedColumnName = "id")
	 Invoice invoice;
	 
	 @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
	 @JoinColumn(name = "foodId", updatable = false, insertable = false, referencedColumnName = "id")
	 Food food;

	public long getFoodId() {
		return foodId;
	}

	public void setFoodId(long foodId) {
		this.foodId = foodId;
	}

	public long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}
	
	 
}
