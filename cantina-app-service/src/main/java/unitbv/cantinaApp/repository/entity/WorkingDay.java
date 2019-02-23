package unitbv.cantinaApp.repository.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import util.LocalDatePersistenceConverter;

import java.time.LocalDate;

@Entity
@NamedQuery(name="WorkingDay.findAll", query="SELECT w FROM WorkingDay w")
public class WorkingDay {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Convert(converter = LocalDatePersistenceConverter.class)
	@Column(name="day", unique=true)
	private LocalDate day;
	
	@Column(name="visible")
	private boolean visible;

	public LocalDate getDay() {
		return day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}
	
	public boolean getVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public WorkingDay(LocalDate day) {
		super();
		this.day = day;
		this.visible = false;
	}

	public WorkingDay() {
		super();
		this.visible = false;
	}
	
	

}
