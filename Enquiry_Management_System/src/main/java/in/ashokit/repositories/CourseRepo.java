package in.ashokit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity_classes.AitCourses;

public interface CourseRepo extends JpaRepository<AitCourses, Integer> {

}
