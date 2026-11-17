package ie.universityofgalway.cs.ct5106.studentreg;

import ie.universityofgalway.cs.ct5106.studentreg.domain.course.CourseId;
import ie.universityofgalway.cs.ct5106.studentreg.domain.student.EmailAddress;
import ie.universityofgalway.cs.ct5106.studentreg.domain.student.Student;
import ie.universityofgalway.cs.ct5106.studentreg.domain.student.StudentName;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class StudentTest {

    @Test
    void createStudent_ShouldSetNameAndEmail() {
        StudentName name = StudentName.of("John", "Doe");
        EmailAddress email = EmailAddress.of("john@example.com");

        Student student = Student.create(name, email);

        assertThat(student.name()).isEqualTo(name);
        assertThat(student.email()).isEqualTo(email);
        assertThat(student.enrollments()).isEmpty();
        assertThat(student.id()).isNotNull();
    }

    @Test
    void changeName_ShouldUpdateName() {
        Student student = Student.create(
                StudentName.of("John", "Doe"),
                EmailAddress.of("john@example.com")
        );

        StudentName newName = StudentName.of("Jane", "Smith");
        student.changeName(newName);

        assertThat(student.name()).isEqualTo(newName);
    }

    @Test
    void enrollTo_ShouldAddEnrollment() {
        Student student = Student.create(
                StudentName.of("John", "Doe"),
                EmailAddress.of("john@example.com")
        );
        CourseId courseId = CourseId.random();

        student.enrollTo(courseId);

        assertThat(student.enrollments())
                .extracting(e -> e.courseId())
                .contains(courseId);
    }

    @Test
    void enrollingToSameCourseTwice_ShouldThrowException() {
        Student student = Student.create(
                StudentName.of("John", "Doe"),
                EmailAddress.of("john@example.com")
        );
        CourseId courseId = CourseId.random();

        student.enrollTo(courseId);

        assertThatThrownBy(() -> student.enrollTo(courseId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Student already enrolled");
    }

    @Test
    void withdrawFromNonEnrolledCourse_ShouldThrowException() {
        Student student = Student.create(
                StudentName.of("John", "Doe"),
                EmailAddress.of("john@example.com")
        );
        CourseId courseId = CourseId.random();

        assertThatThrownBy(() -> student.withdrawFromCourse(courseId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("not enrolled");
    }

//    // Failing test example to illustrate test failure reporting
//    @Test
//    void failingTestExample_ShouldFailForDemo() {
//        Student student = Student.create(
//                StudentName.of("John", "Doe"),
//                EmailAddress.of("john@example.com")
//        );
//
//        // This will fail: student has no enrollments yet
//        assertThat(student.enrollments()).hasSize(1);
//    }
}
