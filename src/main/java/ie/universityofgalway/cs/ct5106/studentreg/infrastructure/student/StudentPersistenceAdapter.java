package ie.universityofgalway.cs.ct5106.studentreg.infrastructure.student;

import ie.universityofgalway.cs.ct5106.studentreg.domain.student.Student;
import ie.universityofgalway.cs.ct5106.studentreg.domain.student.StudentId;
import ie.universityofgalway.cs.ct5106.studentreg.domain.student.EmailAddress;
import ie.universityofgalway.cs.ct5106.studentreg.domain.student.StudentRepository;
import ie.universityofgalway.cs.ct5106.studentreg.domain.student.StudentName;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class StudentPersistenceAdapter implements StudentRepository {

    private final StudentJPARepository jpaRepository;

    public StudentPersistenceAdapter(StudentJPARepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Student> findById(StudentId id) {
        return jpaRepository.findById(id.asUuid())
                .map(this::toDomain);
    }

    @Override
    public List<Student> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Student> findByEmail(EmailAddress email) {
        return jpaRepository.findByEmail(email.value())
                .map(this::toDomain);
    }

    @Override
    public void save(Student student) {
        jpaRepository.save(toEntity(student));
    }

    @Override
    public void deleteById(StudentId id) {
        jpaRepository.deleteById(id.asUuid());
    }

    // ===== Mapping helpers =====
    private Student toDomain(StudentEntity entity) {
        Student student = Student.createWithId(
                StudentId.of(entity.getId()),
                StudentName.of(entity.getName().split(" ")[0],
                                entity.getName().split(" ")[1]),
                EmailAddress.of(entity.getEmail())
        );

        return student;
    }

    private StudentEntity toEntity(Student student) {
        StudentEntity entity = new StudentEntity();
        entity.setId(student.id().asUuid());
        entity.setName(student.name().fullName());
        entity.setEmail(student.email().value());

        return entity;
    }
}
