package in.ashokit.bindingclasses;

import java.sql.Date;

import lombok.Data;

@Data
public class StudentEnquiryForm {

	private Integer studentId;
	
	private String studentName;

	private Long studentPhonNo;

	private Date updatedDate;
	
	private String studentMode;

	private String studentCourse;

	private String studentEnqStatus;
	
	
	

}
