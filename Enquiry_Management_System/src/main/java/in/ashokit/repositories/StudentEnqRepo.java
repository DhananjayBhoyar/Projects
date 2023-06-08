package in.ashokit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity_classes.AitStudentEnquiries;

public interface StudentEnqRepo extends JpaRepository<AitStudentEnquiries, Integer>{

	
}
