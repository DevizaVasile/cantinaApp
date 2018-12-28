package unitbv.cantinaApp.payload.invoice;

import javax.validation.constraints.NotBlank;

public class NewInvoiceRequest {

	@NotBlank
	String email;
	
	@NotBlank
	String day;

	public NewInvoiceRequest(){
		
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
	
	
}
