package in.ashokit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity_classes.AitUserDetails;

public interface UserDtlsRepo extends JpaRepository<AitUserDetails, Integer>{
	
	public AitUserDetails findByUserEmail(String userEmail);
	
	public AitUserDetails findByUserPhonNo(Long userPhonNo);
	
	public AitUserDetails findByUserEmailAndPassWord(String userEmail,String passWord);
}
