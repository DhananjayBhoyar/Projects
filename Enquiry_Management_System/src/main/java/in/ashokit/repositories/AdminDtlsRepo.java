package in.ashokit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity_classes.AitAdminDetails;

public interface AdminDtlsRepo extends JpaRepository<AitAdminDetails,Integer> {

	public AitAdminDetails findByAdminEmailAndAdminPassword(String adminEmail,String adminPassword);

}
