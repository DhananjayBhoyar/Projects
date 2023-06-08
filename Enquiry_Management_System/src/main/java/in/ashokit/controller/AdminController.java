package in.ashokit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import in.ashokit.bindingclasses.AdminLoginForm;
import in.ashokit.constants.AppConstant;
import in.ashokit.entity_classes.AitAdminDetails;
import in.ashokit.entity_classes.AitUserDetails;
import in.ashokit.repositories.AdminDtlsRepo;
import in.ashokit.repositories.UserDtlsRepo;


@Controller
public class AdminController {

	@Autowired
	private AdminDtlsRepo dtlsRepo;

	@Autowired
	private HttpSession session;

	@Autowired
	private UserDtlsRepo userDtlsRepo;

	@GetMapping("/admin")
	public String loginPage(Model model) {

		model.addAttribute("admin", new AdminLoginForm());
		return AppConstant.ADMIN_LOGIN_PAGE;

	}

	@PostMapping("/admin")
	public String adminLogin(@ModelAttribute("admin") AdminLoginForm admin, Model model) {

		AitAdminDetails result = dtlsRepo.findByAdminEmailAndAdminPassword(admin.getAdminEmail(),
				admin.getAdminPassword());

		if (result != null) {
			return "redirect:/Allusers";
		} else {
			model.addAttribute("errorMsg", "Invalid credentials");
			return AppConstant.ADMIN_LOGIN_PAGE;
		}
	}

	@GetMapping("/Allusers")
	public String loadUser(Model model) {

		List<AitUserDetails> findAll = userDtlsRepo.findAll();

		model.addAttribute("alluser", findAll);

		return AppConstant.VIEW_USERS_PAGE;

	}

	@GetMapping("/adminlogin/{userEmail}/{passWord}")
	public String login(@PathVariable("userEmail") String userEmail, @PathVariable("passWord") String passWord,
			Model model) {

		// BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		// AitUserDetails result = userDtlsRepo.findByUserEmail(userEmail);

		AitUserDetails result = userDtlsRepo.findByUserEmailAndPassWord(userEmail, passWord);

		session.setAttribute("userId", result.getUserId());

		return "redirect:/dashboard";

	}

//	@GetMapping("/adminlogin")
//	public String login(@RequestParam("userEmail") String userEmail, @RequestParam("passWord") String passWord, Model model) {
//	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//	    
//	    AitUserDetails result = userDtlsRepo.findByUserEmailAndPassWord(userEmail, passWord);
//	    
//	    if (result != null && passwordEncoder.matches(passWord, result.getPassWord())) {
//	        session.setAttribute("userId", result.getUserId());
//	       
//	    } else {
//	        // Invalid login
//	        // You can handle the invalid login scenario here
//	        
//	    }
//	    return "redirect:/dashboard";
//	}

//	@GetMapping("/loginn")
//	public String loginPagee(Model model) {
//
//		model.addAttribute("login", new LoginForm());
//		return "login";
//
//	}

//	  @GetMapping("/admin/employee/{id}")
//	    public String employeeDashboard(@PathVariable Integer userId, Model model) {
//		  Optional<AitUserDetails> findById = userDtlsRepo.findById(userId);
//		
//		  if(findById != null) {
//			  
//			  AitUserDetails aitUserDetails = findById.get();
//			  
//			  model.addAttribute("data", aitUserDetails);
//			  
//		  }
//		
//		
//	        
//	        return "empdashboard";
//	    }

}
