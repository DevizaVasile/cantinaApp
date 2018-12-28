package unitbv.cantinaApp.repository.entity;

import java.io.Serializable;

public class InvoiceFoodId implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long invoiceId;

	 private long foodId;
	 
	 public int hashCode() {
	  return (int)(invoiceId + foodId);
	 }

	 public boolean equals(Object object) {
	  if (object instanceof InvoiceFoodId) {
		  InvoiceFoodId otherId = (InvoiceFoodId) object;
	   return (otherId.invoiceId == this.invoiceId) && (otherId.foodId == this.foodId);
	  }
	  return false;
	 }

	}