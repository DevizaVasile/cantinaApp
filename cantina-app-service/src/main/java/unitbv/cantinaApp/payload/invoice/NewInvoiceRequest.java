package unitbv.cantinaApp.payload.invoice;
import unitbv.cantinaApp.payload.order.OrderRepresentation;

import java.util.List;

import javax.validation.constraints.NotBlank;

public class NewInvoiceRequest {

	@NotBlank
	String email;
	@NotBlank
	String day;
	List<OrderRepresentation> order;
	Double sumRON;

	public NewInvoiceRequest(){
		super();
	}
	
	public NewInvoiceRequest(@NotBlank String email, @NotBlank String day, List<OrderRepresentation> order,
			Double sumRON) {
		super();
		this.email = email;
		this.day = day;
		this.order = order;
		this.sumRON = sumRON;
	}



	public List<OrderRepresentation> getOrder() {
		return order;
	}

	public void setOrder(List<OrderRepresentation> order) {
		this.order = order;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Double getSumRON() {
		return sumRON;
	}

	public void setSumRON(Double sumRON) {
		this.sumRON = sumRON;
	}
}
