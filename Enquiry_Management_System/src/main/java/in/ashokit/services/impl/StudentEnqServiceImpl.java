package in.ashokit.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.bindingclasses.DashboardBindResponse;
import in.ashokit.bindingclasses.EnquirySearchCriteria;
import in.ashokit.bindingclasses.StudentEnquiryForm;
import in.ashokit.entity_classes.AitCourses;
import in.ashokit.entity_classes.AitEnquiryStatus;
import in.ashokit.entity_classes.AitStudentEnquiries;
import in.ashokit.entity_classes.AitUserDetails;
import in.ashokit.repositories.CourseRepo;
import in.ashokit.repositories.EnqStatusRepo;
import in.ashokit.repositories.StudentEnqRepo;
import in.ashokit.repositories.UserDtlsRepo;
import in.ashokit.services.StudentEnqService;

@Service
public class StudentEnqServiceImpl implements StudentEnqService {

	@Autowired
	private UserDtlsRepo repo;

	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private EnqStatusRepo enqStatusRepo;

	@Autowired
	private StudentEnqRepo studentEnqRepo;

	@Autowired
	private HttpSession session;

	DashboardBindResponse dashboardData = new DashboardBindResponse();

	@Override
	public DashboardBindResponse getDashBoardData(Integer userId) {

		Optional<AitUserDetails> findById = repo.findById(userId);

		if (findById.isPresent()) {

			AitUserDetails userEntity = findById.get();

			List<AitStudentEnquiries> enquiries = userEntity.getEnquiries();
			Integer total = enquiries.size();

			Integer enrolledCount = enquiries.stream().filter(e -> e.getStudentEnqStatus().equals("Enrolled"))
					.collect(Collectors.toList()).size();

			int lostCount = enquiries.stream().filter(e -> e.getStudentEnqStatus().equals("Lost"))
					.collect(Collectors.toList()).size();

			int newCount = enquiries.stream().filter(e -> e.getStudentEnqStatus().equals("New"))
					.collect(Collectors.toList()).size();

			dashboardData.setTotalEnqCount(total);
			dashboardData.setEnrolledCount(enrolledCount);
			dashboardData.setLostCount(lostCount);
			dashboardData.setNewCount(newCount);

		}

		return dashboardData;
	}

	@Override
	public List<String> getCourses() {

		List<AitCourses> findAll = courseRepo.findAll();

		List<String> coursesList = new ArrayList<>();

		for (AitCourses entity : findAll) {

			coursesList.add(entity.getCourses());

		}

		return coursesList;
	}

	@Override
	public List<String> getEnqStatus() {

		List<AitEnquiryStatus> findAll = enqStatusRepo.findAll();

		List<String> statusList = new ArrayList<>();

		for (AitEnquiryStatus entity : findAll) {

			statusList.add(entity.getEnquiryStatus());

		}
		return statusList;
	}

	@Override
	public boolean saveEnquiries(StudentEnquiryForm form) {

		AitStudentEnquiries enqEntity = new AitStudentEnquiries();

		BeanUtils.copyProperties(form, enqEntity);

		Integer userId = (Integer) session.getAttribute("userId");

		// AitUserDetails aitUserDetails = repo.findById(userId).get();
		Optional<AitUserDetails> findById = repo.findById(userId);

		if (findById.isPresent()) {

			AitUserDetails aitUserDetails = findById.get();
			enqEntity.setStudentUserId(aitUserDetails);
		}

		studentEnqRepo.save(enqEntity);

		return true;
	}

	@Override
	public List<AitStudentEnquiries> getEnquiries() {

		Integer userId = (Integer) session.getAttribute("userId");
		Optional<AitUserDetails> findById = repo.findById(userId);

		if (findById.isPresent()) {

			AitUserDetails aitUserDetails = findById.get();
			//List<AitStudentEnquiries> enquiries = aitUserDetails.getEnquiries();
			return aitUserDetails.getEnquiries();

		}

		return Collections.emptyList();
	}

	@Override
	public List<AitStudentEnquiries> getFilterEnquiries(EnquirySearchCriteria criteria, Integer userId) {
		Optional<AitUserDetails> findById = repo.findById(userId);

		if (findById.isPresent()) {

			AitUserDetails aitUserDetails = findById.get();
			List<AitStudentEnquiries> enquiries = aitUserDetails.getEnquiries();

			if (null != criteria.getCourseName() && !"".equals(criteria.getCourseName())) {

				enquiries = enquiries.stream().filter(e -> e.getStudentCourse().equals(criteria.getCourseName()))
						.collect(Collectors.toList());
			}

			if (null != criteria.getEnqStatus() && !"".equals(criteria.getEnqStatus())) {

				enquiries = enquiries.stream().filter(g -> g.getStudentEnqStatus().equals(criteria.getEnqStatus()))
						.collect(Collectors.toList());
			}

			if (null != criteria.getClassMode() && !"".equals(criteria.getClassMode())) {

				enquiries = enquiries.stream().filter(l -> l.getStudentMode().equals(criteria.getClassMode()))
						.collect(Collectors.toList());
			}

			return enquiries;

		}

		return Collections.emptyList();
	}

}
