package in.ashokit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity_classes.AitEnquiryStatus;

public interface EnqStatusRepo extends JpaRepository<AitEnquiryStatus, Integer> {

}
