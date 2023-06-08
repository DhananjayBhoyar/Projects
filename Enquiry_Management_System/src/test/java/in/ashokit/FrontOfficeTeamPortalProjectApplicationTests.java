package in.ashokit;

import java.util.Collection;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;

import in.ashokit.bindingclasses.StudentEnquiryForm;
import in.ashokit.constants.AppConstant;
import in.ashokit.controller.EnquiryController;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class FrontOfficeTeamPortalProjectApplicationTests {

	@Autowired
    private EnquiryController controller;
	
	@Test
    void testAddEnq() {
		
		
		
		
		Model model =new Model() {

			@Override
			public Model addAttribute(String attributeName, Object attributeValue) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Model addAttribute(Object attributeValue) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Model addAllAttributes(Collection<?> attributeValues) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Model addAllAttributes(Map<String, ?> attributes) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Model mergeAttributes(Map<String, ?> attributes) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean containsAttribute(String attributeName) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public Object getAttribute(String attributeName) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map<String, Object> asMap() {
				// TODO Auto-generated method stub
				return null;
			}
			
		};
		
		StudentEnquiryForm form=new StudentEnquiryForm();
		form.setStudentName("jhon");
		form.setStudentCourse("java");
		
		String result = controller.addEnq(form, model);
		
		 Assertions.assertEquals(AppConstant.ADD_ENQUIRY_PAGE, result);
		
		
		
	}

}
