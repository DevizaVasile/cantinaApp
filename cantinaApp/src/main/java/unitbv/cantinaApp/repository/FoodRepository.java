package unitbv.cantinaApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import unitbv.cantinaApp.repository.entity.Food;

public interface FoodRepository  extends JpaRepository<Food,Long>  {
	
	public Optional<Food> findByName(String name);

}
