package ie.universityofgalway.cs.ct5106.studentreg.config;

import ie.universityofgalway.cs.ct5106.studentreg.application.enrolment.*;
import ie.universityofgalway.cs.ct5106.studentreg.application.student.*;
import ie.universityofgalway.cs.ct5106.studentreg.domain.student.StudentRepository;
import ie.universityofgalway.cs.ct5106.studentreg.infrastructure.student.StudentJPARepository;
import ie.universityofgalway.cs.ct5106.studentreg.infrastructure.student.StudentPersistenceAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {


//    @Bean
//    public StudentRepository studentRepository() {
//        return new InMemoryStudentRepository();
//    }

    public StudentRepository studentRepository(StudentJPARepository StudentJPARepository) {
        return new StudentPersistenceAdapter(StudentJPARepository);
    }

    @Bean
    public AddStudentUseCase addStudentUseCase(StudentRepository studentRepository) {
        return new AddStudentUseCase(studentRepository);
    }

    @Bean
    public ViewAllStudentsUseCase viewAllStudentsUseCase(StudentRepository studentRepository) {
        return new ViewAllStudentsUseCase(studentRepository);
    }

    @Bean
    public UpdateStudentDetailsUseCase updateStudentDetailsUseCase(StudentRepository studentRepository) {
        return new UpdateStudentDetailsUseCase(studentRepository);
    }

    @Bean
    public DeleteStudentUseCase deleteStudentUseCase(StudentRepository studentRepository) {
        return new DeleteStudentUseCase(studentRepository);
    }

    @Bean
    public EnrolStudentToCourseUseCase enrolStudentToCourseUseCase(StudentRepository studentRepository) {
        return new EnrolStudentToCourseUseCase(studentRepository);
    }

    @Bean
    WithdrawStudentFromCourseUseCase withdrawStudentFromCourseUseCase(StudentRepository studentRepository) {
        return new WithdrawStudentFromCourseUseCase(studentRepository);
    }

    @Bean
    CompleteCourseUseCase completeCourseUseCase(StudentRepository studentRepository) {
        return new CompleteCourseUseCase(studentRepository);
    }

    @Bean
    RemoveStudentFromCourseUseCase removeStudentFromCourseUseCase(StudentRepository studentRepository) {
        return new RemoveStudentFromCourseUseCase(studentRepository);
    }

    @Bean
    ViewStudentCoursesUseCase viewStudentCoursesUseCase(StudentRepository studentRepository) {
        return new ViewStudentCoursesUseCase(studentRepository);
    }



}
