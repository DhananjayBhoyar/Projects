package in.ashokit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.ashokit.bindingclasses.LoginForm;
import in.ashokit.bindingclasses.SignupForm;
import in.ashokit.bindingclasses.UnlockForm;
import in.ashokit.constants.AppConstant;
import in.ashokit.constants.ModelAttributes;
import in.ashokit.services.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/signup")
	public String handleSignUp(@Validated @ModelAttribute("user") SignupForm form, BindingResult result, Model model) {

		if (result.hasErrors()) {

			return AppConstant.SIGNUP_PAGE;
		}

		boolean status = userService.signUp(form);

		if (status) {

			model.addAttribute(ModelAttributes.SUCCESS_MSG, AppConstant.ACCOUNT_CREATED);
		} else {
			model.addAttribute(ModelAttributes.ERROR_MSG, AppConstant.EMAIL_TAKEN);
		}

		return AppConstant.SIGNUP_PAGE;

	}

	@GetMapping("/login")
	public String loginPage(Model model) {

		model.addAttribute(ModelAttributes.LOGIN, new LoginForm());
		return AppConstant.LOGIN_PAGE;

	}

	@PostMapping("/login")
	public String login(@ModelAttribute("login") LoginForm loginForm, Model model) {

		String status = userService.login(loginForm);
		if (status.contains("Success")) {

			return "redirect:/dashboard";
		}

		model.addAttribute(ModelAttributes.ERROR_MSG, status);

		return AppConstant.LOGIN_PAGE;
	}

	@GetMapping("/signup")
	public String signUpPage(Model model) {

		model.addAttribute(ModelAttributes.USER, new SignupForm());

		return AppConstant.SIGNUP_PAGE;

	}

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email, Model model) {

		UnlockForm fobj = new UnlockForm();
		fobj.setEmail(email);

		model.addAttribute(ModelAttributes.UNLOCK, fobj);

		return AppConstant.UNLOCK_PAGE;

	}

	@PostMapping("/unlock")
	public String unlockUserAccount(@ModelAttribute("unlock") UnlockForm unlock, Model model) {

		// System.out.println(unlock);

		if (unlock.getNewPwd().equals(unlock.getConfirmPwd())) {

			boolean status = userService.unlockAccount(unlock);
			if (status) {
				model.addAttribute(ModelAttributes.SUCCESS_MSG, AppConstant.ACCOUNT_UNLOCKED);

			} else {
				model.addAttribute(ModelAttributes.ERROR_MSG, AppConstant.TEMP_ERROR);

			}
		} else {
			model.addAttribute(ModelAttributes.ERROR_MSG, AppConstant.PD_SAME);
		}

		return AppConstant.UNLOCK_PAGE;

	}

	@GetMapping("/forgot")
	public String forgotPwd() {

		return AppConstant.FORGOTPD;

	}

	@PostMapping("/forgotPwd")
	public String forgot(@RequestParam("email") String email, Model model) {

		// System.out.println(email);

		boolean status = userService.emailForForgot(email);

		if (status) {

			model.addAttribute(ModelAttributes.SUCCESS_MSG, AppConstant.PD_SENT);

		} else {

			model.addAttribute(ModelAttributes.ERROR_MSG, AppConstant.INVALID_EMAIL);

		}

		return AppConstant.FORGOTPD;

	}

}
