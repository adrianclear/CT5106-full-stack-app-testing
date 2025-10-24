package ie.universityofgalway.cs.ct5106.studentreg.infrastructure.student;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "student")
public class StudentEntity {

    @Id
    private UUID id;

    private String name;
    private String email;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<EnrolmentEntity> enrolments = new HashSet<>();

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<EnrolmentEntity> getEnrolments() {
        return enrolments;
    }

    public void setEnrolments(Set<EnrolmentEntity> enrolments) {
        this.enrolments = enrolments;
    }
}
