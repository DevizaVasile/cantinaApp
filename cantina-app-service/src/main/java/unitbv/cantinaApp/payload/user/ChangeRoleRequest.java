package unitbv.cantinaApp.payload.user;

public class ChangeRoleRequest {
	String role;
	String email;
	
	public ChangeRoleRequest() {
		super();
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
