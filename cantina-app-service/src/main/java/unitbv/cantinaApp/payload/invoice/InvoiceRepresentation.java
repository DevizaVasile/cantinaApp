package unitbv.cantinaApp.payload.invoice;

public class InvoiceRepresentation {
	
	String email;
	String day;
	int status;
	
	public InvoiceRepresentation(String email, String day, int status) {
		super();
		this.email = email;
		this.day = day;
		this.status = status;
	}
	public InvoiceRepresentation() {
		super();
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}	
}
