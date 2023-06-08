package in.ashokit.services;

import in.ashokit.bindingclasses.LoginForm;
import in.ashokit.bindingclasses.SignupForm;
import in.ashokit.bindingclasses.UnlockForm;

public interface UserService {

	public boolean signUp(SignupForm form);

	public boolean unlockAccount(UnlockForm form);

	public String login(LoginForm form);

	public boolean emailForForgot(String email);
}
