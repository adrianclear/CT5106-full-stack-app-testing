package ie.universityofgalway.cs.ct5106.studentreg.infrastructure.student;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "enrolment")
public class EnrolmentEntity {

    @Id
    private UUID id;

//    The foreign key column in the enrolment table is called student_id (from my schema).
//    That column refers to the id column of the student table.
//    nullable = false means every enrolment must have a student — it can’t be NULL.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;

    private UUID courseId;
    private String status;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public UUID getCourseId() {
        return courseId;
    }

    public void setCourseId(UUID courseId) {
        this.courseId = courseId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
