package unitbv.cantinaApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import unitbv.cantinaApp.repository.entity.Food;
import unitbv.cantinaApp.repository.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu,Long> {
	
	public List<Menu> findAllByDate(String date);
	public List<Menu> findByFoodAndDate(Food food, String date);
}
