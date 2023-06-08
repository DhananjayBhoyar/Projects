package in.ashokit.entity_classes;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class AitStudentEnquiries {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer studentId;

	private String studentName;

	private Long studentPhonNo;

	private String studentMode;

	private String studentCourse;

	private String studentEnqStatus;

	@CreationTimestamp
	private Date createdDate;

	@UpdateTimestamp
	private Date updatedDate;

	@ManyToOne
	@JoinColumn(name = "studentUserId")
	private AitUserDetails studentUserId;

}
