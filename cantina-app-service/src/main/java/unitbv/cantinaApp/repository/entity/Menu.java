package unitbv.cantinaApp.repository.entity;



import javax.persistence.*;

@Entity
@NamedQuery(name="Menu.findAll", query="SELECT m FROM Menu m")
public class Menu {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column
	private String date;
	
	@ManyToOne( fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    @JoinColumn(name = "food_id")
    private Food food;
	
	public Menu() {
		super();
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public Long getId() {
		return id;
	}
	
	
}
