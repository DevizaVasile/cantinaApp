package unitbv.cantinaApp.payload.incident;

public class NewIncidentRepresentation {
 
	String userEmail;
	
	int generic;
	
	String day;
	
	String createdAt;
	
	String text;
	
	public NewIncidentRepresentation() {
		super();
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userId) {
		this.userEmail = userId;
	}

	public int getGeneric() {
		return generic;
	}

	public void setGeneric(int generic) {
		this.generic = generic;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
