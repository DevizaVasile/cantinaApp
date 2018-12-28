package unitbv.cantinaApp;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import unitbv.cantinaApp.repository.FoodRepository;



@SpringBootApplication
@EntityScan(basePackageClasses = { 
		CantinaAppApplication.class,
		Jsr310JpaConverters.class 
})
public class CantinaAppApplication {
	
	@Autowired
	FoodRepository foodRepo;

	public static void main(String[] args) {
		SpringApplication.run(CantinaAppApplication.class, args);	
	}
	
}
