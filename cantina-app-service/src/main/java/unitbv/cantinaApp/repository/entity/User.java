package unitbv.cantinaApp.repository.entity;

import javax.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private BigDecimal balance;

	@Column(name="created_at")
	private Timestamp createdAt;

	private String email;

	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;

	private String password;

	@Column(name="updated_at")
	private Timestamp updatedAt;

	//bi-directional many-to-many association to Role
	@ManyToMany
	@JoinTable(
		name="user_roles"
		, joinColumns={
			@JoinColumn(name="user_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="role_id")
			}
		)
	private List<Role> roles;
	
	@OneToMany(mappedBy="user", fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
	private List<Invoice> invoices;

	public User() {
	}

	public User(String firstName, String lastName, String email, String password) {
		this.firstName=firstName;
		this.lastName=lastName;
		this.email=email;
		this.password=password;
		this.balance=BigDecimal.valueOf(0);
		this.createdAt=Timestamp.from(Instant.now());
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	public void addInvoice(Invoice invoice) {
		this.invoices.add(invoice);
		invoice.setUser(this);
	}
	
	public void addRole(Role role) {
		if(! this.roles.contains(role)) {
			this.roles.add(role);
		}
	}
	
	public void removeRole(Role role) {
		if(this.roles.contains(role)) {
			this.roles.remove(role);
		}
	}
}