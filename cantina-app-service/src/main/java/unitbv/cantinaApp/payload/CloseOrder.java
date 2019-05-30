package unitbv.cantinaApp.payload;

public class CloseOrder {
	
	private String userEmail;
	private String day;
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public CloseOrder() {
		super();
	}
}
