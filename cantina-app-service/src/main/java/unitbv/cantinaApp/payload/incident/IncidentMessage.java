package unitbv.cantinaApp.payload.incident;

public class IncidentMessage {
	Long userId;
	String day;
	String response;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public IncidentMessage() {
		super();
	}
}
