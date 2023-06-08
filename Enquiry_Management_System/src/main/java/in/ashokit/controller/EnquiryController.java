package in.ashokit.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.ashokit.bindingclasses.DashboardBindResponse;
import in.ashokit.bindingclasses.EnquirySearchCriteria;
import in.ashokit.bindingclasses.StudentEnquiryForm;
import in.ashokit.constants.AppConstant;
import in.ashokit.entity_classes.AitStudentEnquiries;
import in.ashokit.repositories.StudentEnqRepo;
import in.ashokit.services.StudentEnqService;

@Controller
public class EnquiryController {

	@Autowired
	private StudentEnqRepo studentEnqRepo;
	@Autowired
	private StudentEnqService studentEnquiries;

	@Autowired
	private HttpSession session;

	@GetMapping("/logout")
	public String logout() {

		session.invalidate();

		return AppConstant.INDEX_PAGE;
	}

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		
//		Object nullObject = null;
//        // Accessing a member or invoking a method on nullObject
//        nullObject.toString();

		Integer userId = (Integer) session.getAttribute("userId");

		DashboardBindResponse dashBoardData = studentEnquiries.getDashBoardData(userId);

		model.addAttribute("dashBoardData", dashBoardData);

		return AppConstant.DASBOARD_PAGE;

	}

	@GetMapping("/enquiry")
	public String addEnquiry(Model model) {

		initForm(model);
		
		return AppConstant.ADD_ENQUIRY_PAGE;

	}

	private void initForm(Model model) {
		List<String> courses = studentEnquiries.getCourses();

		List<String> enqStatus = studentEnquiries.getEnqStatus();

		StudentEnquiryForm formObj = new StudentEnquiryForm();

		model.addAttribute("formObj", formObj);
		model.addAttribute("coursesNames", courses);
		model.addAttribute("enqStatusNames", enqStatus);
	}

	@PostMapping("/addEnq")
	public String addEnq(@ModelAttribute("formObj") StudentEnquiryForm formObj, Model model) {

		boolean status = studentEnquiries.saveEnquiries(formObj);

		if (status) {

			model.addAttribute("sucessMsg", AppConstant.ADD_ENQ);

		} else {

			model.addAttribute("errorMsg", AppConstant.PROMB);

		}

		return AppConstant.ADD_ENQUIRY_PAGE;
	}
	
	@GetMapping("/enquiries")
	public String viewEnquiries(Model model) {
		
		initForm(model);
		List<AitStudentEnquiries> enquiries = studentEnquiries.getEnquiries();
		model.addAttribute("enquiries", enquiries);
		
		return AppConstant.VIEW_ENAUIRIES_PAGE;
		
	}
	
	@GetMapping("/filter-enquiries")
	public String getFilterEnq(@RequestParam String cname,
			@RequestParam String status,
			@RequestParam String mode,
			Model model) {
		EnquirySearchCriteria criteria=new EnquirySearchCriteria();
		criteria.setCourseName(cname);
		criteria.setClassMode(mode);
		criteria.setEnqStatus(status);
		
		//System.out.println(criteria);
		
		Integer userId = (Integer) session.getAttribute("userId");
		List<AitStudentEnquiries> filterEnquiries = studentEnquiries.getFilterEnquiries(criteria, userId);
 
		model.addAttribute("enquiries",filterEnquiries);
		
		//System.out.println(filterEnquiries);
		return AppConstant.FILTER_ENQUIRIES;
		
	}
	
	@GetMapping("/edit")
	public String editById(@RequestParam("studentId") Integer studentId, Model model) {
		Optional<AitStudentEnquiries> findById = studentEnqRepo.findById(studentId);
		
		if (findById.isPresent()) {

			 AitStudentEnquiries formObj = findById.get();
				List<String> courses = studentEnquiries.getCourses();

				List<String> enqStatus = studentEnquiries.getEnqStatus();
				
			model.addAttribute("coursesNames", courses);
			model.addAttribute("enqStatusNames", enqStatus);
			model.addAttribute("formObj", formObj);
		}
		
		return AppConstant.ADD_ENQUIRY_PAGE;		
	}

}
