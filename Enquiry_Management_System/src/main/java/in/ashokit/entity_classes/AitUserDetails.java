package in.ashokit.entity_classes;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class AitUserDetails {
   
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer userId;
	
	private String userName;
	
	@Column(unique = true )
	private String userEmail;
	
	@Column(unique = true )
	private Long userPhonNo;
	
	private String passWord;
	
	private String accStatus;
	
	@OneToMany(mappedBy = "studentUserId",cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	@JsonIgnore
	private List<AitStudentEnquiries>enquiries;
	
}
