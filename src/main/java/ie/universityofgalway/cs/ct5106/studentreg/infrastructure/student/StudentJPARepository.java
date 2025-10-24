package ie.universityofgalway.cs.ct5106.studentreg.infrastructure.student;

import ie.universityofgalway.cs.ct5106.studentreg.domain.student.StudentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentJPARepository extends JpaRepository<StudentEntity, UUID> {
    Optional<StudentEntity> findByEmail(String email);    // Derived query method to find StudentEntity by email
}
