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
	
	public void addQuantity(Integer quantity) {
		this.quantity+=quantity;
	}
	
	public void substractQuantity(Integer quantity) {
		this.quantity-=quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (foodId ^ (foodId >>> 32));
		result = prime * result + (int) (invoiceId ^ (invoiceId >>> 32));
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
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
		InvoiceFood other = (InvoiceFood) obj;
		if (foodId != other.foodId)
			return false;
		if (invoiceId != other.invoiceId)
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		return true;
	}
	
	
}
