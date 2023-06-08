package in.ashokit.services.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.bindingclasses.LoginForm;
import in.ashokit.bindingclasses.SignupForm;
import in.ashokit.bindingclasses.UnlockForm;
import in.ashokit.constants.AppConstant;
import in.ashokit.entity_classes.AitUserDetails;
import in.ashokit.repositories.UserDtlsRepo;
import in.ashokit.services.UserService;
import in.ashokit.utils.EmailUtil;
import in.ashokit.utils.PwdUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDtlsRepo repo;

	@Autowired
	private EmailUtil emailUtil;

	@Autowired
	private HttpSession session;

	@Override
	public boolean signUp(SignupForm form) {
		// Copy data obj from binding to entity

		AitUserDetails email = repo.findByUserEmail(form.getUserEmail());
		AitUserDetails phono = repo.findByUserPhonNo(form.getUserPhonNo());

		if (phono != null || email != null) {

			return false;
		}

		AitUserDetails entity = new AitUserDetails();
		BeanUtils.copyProperties(form, entity);

		// Generate random password

		String tempPwd = PwdUtils.generateRandomPwd();
		entity.setPassWord(tempPwd);

		// Set Account by default locked
		entity.setAccStatus("Locked");

		// Insert data
		repo.save(entity);

		// Send temp pwd to email

		String to = form.getUserEmail();

		String subject = AppConstant.EMAIL_UNL_MSG;

		// String preExistingString = "Hello, ";
		// StringBuilder greeting = new StringBuilder(preExistingString);

		StringBuilder body = new StringBuilder("");

		body.append(AppConstant.EMAIL_MSG_1);

		body.append(AppConstant.EMAIL_MSG_2 + tempPwd);

		body.append("<br/>");

		body.append(AppConstant.EMAIL_MSG_3 + to + AppConstant.EMAIL_MSG_4);

		emailUtil.sendEmail(to, subject, body.toString());

		return true;
	}

	public boolean unlockAccount(UnlockForm form) {

		AitUserDetails entity = repo.findByUserEmail(form.getEmail());

		if (entity.getPassWord().equals(form.getTempPwd())) {
			entity.setPassWord(form.getNewPwd());
			entity.setAccStatus("Unlocked");

			repo.save(entity);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public String login(LoginForm form) {

		String pwd = form.getPwd();

		AitUserDetails result = repo.findByUserEmailAndPassWord(form.getEmail(), pwd);

		if (result == null) {
			return AppConstant.INVALID_CRED;
		}
		if (result.getAccStatus().equals("Locked")) {
			return AppConstant.ACC_LOCKED;
		}

		session.setAttribute("userId", result.getUserId());
		return "Success";

	}

	@Override
	public boolean emailForForgot(String email) {

		AitUserDetails entity = repo.findByUserEmail(email);
		if (entity == null) {

			return false;
		}
		String subject = AppConstant.RECOVER_PD;
		String body = AppConstant.YOUR_PD + entity.getPassWord();
		emailUtil.sendEmail(email, subject, body);

		return true;
	}

}
