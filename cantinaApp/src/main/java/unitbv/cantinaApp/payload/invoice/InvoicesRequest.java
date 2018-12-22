package unitbv.cantinaApp.payload.invoice;

import javax.validation.constraints.NotBlank;

public class InvoicesRequest {
	@NotBlank
	String email;

	public InvoicesRequest() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
