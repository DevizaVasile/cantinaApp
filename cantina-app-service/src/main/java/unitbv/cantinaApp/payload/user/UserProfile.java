package unitbv.cantinaApp.payload.user;

import java.math.BigDecimal;

public class UserProfile {
	public String firstName;
	public String lastName;
	public BigDecimal balance;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public UserProfile(String firstName, String lastName, BigDecimal balance) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.balance = balance;
	}
	public UserProfile() {
		super();
	}
	
	
}
