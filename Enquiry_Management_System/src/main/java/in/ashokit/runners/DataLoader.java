package in.ashokit.runners;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.ashokit.entity_classes.AitCourses;
import in.ashokit.entity_classes.AitEnquiryStatus;
import in.ashokit.repositories.CourseRepo;
import in.ashokit.repositories.EnqStatusRepo;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private EnqStatusRepo enqStatusRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		courseRepo.deleteAll();

		AitCourses c1 = new AitCourses();
		c1.setCourses("Java");

		AitCourses c2 = new AitCourses();
		c2.setCourses("Python");

		AitCourses c3 = new AitCourses();
		c3.setCourses("DevOps");

		AitCourses c4 = new AitCourses();
		c4.setCourses("AWS");

		courseRepo.saveAll(Arrays.asList(c1, c2, c3, c4));

		enqStatusRepo.deleteAll();

		AitEnquiryStatus a1 = new AitEnquiryStatus();
		a1.setEnquiryStatus("New");

		AitEnquiryStatus a2 = new AitEnquiryStatus();
		a2.setEnquiryStatus("Enrolled");

		AitEnquiryStatus a3 = new AitEnquiryStatus();
		a3.setEnquiryStatus("Lost");

		enqStatusRepo.saveAll(Arrays.asList(a1, a2, a3));
	}

}
