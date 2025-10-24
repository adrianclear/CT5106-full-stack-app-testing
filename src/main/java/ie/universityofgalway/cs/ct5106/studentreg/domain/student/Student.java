package ie.universityofgalway.cs.ct5106.studentreg.domain.student;

import ie.universityofgalway.cs.ct5106.studentreg.domain.course.CourseId;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//Student aggregate stores enrollments with just a CourseId - don’t include the full Course entity in the Student package
//The aggregate root (Student) only cares about the identity of the course, not its internal state.
//Prevents the Student aggregate from depending on Course logic (like course schedules, prerequisites, etc.).
//Keeps the Student aggregate focused on its own invariants and behaviors.
//Avoids leaking Course details into the Student context, maintaining clear boundaries.
public class Student {
    private final StudentId id;
    private StudentName name;
    private EmailAddress email;
    private final Set<Enrolment> enrolments = new HashSet<>();

    // Aggregate root creation should go through a factory method or constructor that enforces invariants.
    // Public constructor that allows raw IDs is generally discouraged (can you trust the caller?)
    private Student(StudentId id, StudentName name, EmailAddress email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static Student create(StudentName name, EmailAddress email) {
        // enforce invariants here if needed
        return new Student(StudentId.random(), name, email);
    }

    public static Student createWithId(StudentId id, StudentName name, EmailAddress email) {
        // enforce invariants here if needed
        return new Student(id, name, email);
    }

    public StudentId id() {
        return id;
    }

    public StudentName name() {
        return name;
    }

    public EmailAddress email() {
        return email;
    }

    public Set<Enrolment> enrollments() {
        return Collections.unmodifiableSet(enrolments);
    }

    // domain behavior
    public void changeName(StudentName newName) {
        this.name = newName;
    }

    public void changeEmail(EmailAddress newEmail) {
        this.email = newEmail;
    }

    //Invariant involves multiple Enrollment entities, so it must be enforced in the aggregate root.
    public void enrollTo(CourseId courseId) {
        boolean alreadyEnrolled = enrolments.stream()
                .anyMatch(e -> e.courseId().equals(courseId));
        if (alreadyEnrolled) {
            throw new IllegalStateException("Student already enrolled in course: " + courseId);
        }
        enrolments.add(Enrolment.create(courseId));
    }

    public void removeEnrollment(CourseId courseId) {
        Enrolment toRemove = enrolments.stream()
                .filter(e -> e.courseId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Student not enrolled in course: " + courseId));
        enrolments.remove(toRemove);
    }

    public void withdrawFromCourse(CourseId courseId) {
        Enrolment e = enrolments.stream()
                .filter(en -> en.courseId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Student not enrolled in course: " + courseId));
        e.withdraw();
    }

    public void completeCourse(CourseId courseId) {
        Enrolment e = enrolments.stream()
                .filter(en -> en.courseId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Student not enrolled in course: " + courseId));
        e.complete();
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        return id.equals(((Student) o).id);
    }

    @Override public int hashCode() {
        return Objects.hash(id);
    }
}
