package in.ashokit.bindingclasses;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SignupForm {

	@NotBlank(message="**This field is mandetory**")
	private String userName;
	
	@NotBlank(message="**This field is mandetory**")
	private String userEmail;
	
	@NotNull(message="**This field is mandetory**")
	private Long userPhonNo;
}
