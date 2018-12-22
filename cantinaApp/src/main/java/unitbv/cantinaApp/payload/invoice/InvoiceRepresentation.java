package unitbv.cantinaApp.payload.invoice;

public class InvoiceRepresentation {
	
	String email;
	String day;
	
	public InvoiceRepresentation(String email, String day) {
		super();
		this.email = email;
		this.day = day;
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
	
	
}
