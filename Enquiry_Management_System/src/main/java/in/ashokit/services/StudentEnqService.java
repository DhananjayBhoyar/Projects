package in.ashokit.services;

import java.util.List;

import in.ashokit.bindingclasses.DashboardBindResponse;
import in.ashokit.bindingclasses.EnquirySearchCriteria;
import in.ashokit.bindingclasses.StudentEnquiryForm;
import in.ashokit.entity_classes.AitStudentEnquiries;

public interface StudentEnqService {

	public DashboardBindResponse getDashBoardData(Integer userId);

	public List<String> getCourses();

	public List<String> getEnqStatus();

	public boolean saveEnquiries(StudentEnquiryForm form);

	public List<AitStudentEnquiries> getEnquiries();

	public List<AitStudentEnquiries> getFilterEnquiries(EnquirySearchCriteria criteria, Integer userId);

}
