package unitbv.cantinaApp.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name="SpecificIncident.findAll", query="SELECT i FROM Incident i")
public class Incident {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private Long userId;
	
	@Column
	private Long invoiceId;
	
	@Column
	private Long foodId;
	
	@Column
	private String text;
	
	@Column
	private String response;
	
	@Column
	private Integer status;
	
	@Column
	String day;
	
	@Column
	String createdAt;
	
	public Incident() {
		super();
	}

	public Incident(Long id, String text, boolean isGeneric, String day, String createdAt, Long userId) {
		super();
		if(isGeneric) {
			this.invoiceId = id;
		}
		else {
			this.foodId = id;
		}
		this.text = text;
		this.status = 0;
		this.day = day;
		this.createdAt = createdAt;
		this.userId = userId;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getFoodId() {
		return foodId;
	}

	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
