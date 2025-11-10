package ie.universityofgalway.cs.ct5106.studentreg;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.universityofgalway.cs.ct5106.studentreg.application.student.AddStudentUseCase;
import ie.universityofgalway.cs.ct5106.studentreg.application.student.DeleteStudentUseCase;
import ie.universityofgalway.cs.ct5106.studentreg.application.student.UpdateStudentDetailsUseCase;
import ie.universityofgalway.cs.ct5106.studentreg.application.student.ViewAllStudentsUseCase;
import ie.universityofgalway.cs.ct5106.studentreg.application.student.dto.AddStudentResponse;
import ie.universityofgalway.cs.ct5106.studentreg.infrastructure.rest.StudentController;
import ie.universityofgalway.cs.ct5106.studentreg.infrastructure.rest.dto.AddStudentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerIT {

    @Autowired
    private MockMvc mockMvc;



    @MockitoBean
    AddStudentUseCase addStudentUseCase;
    @MockitoBean
    private ViewAllStudentsUseCase viewAllStudentsUseCase;
    @MockitoBean
    private UpdateStudentDetailsUseCase updateStudentDetailsUseCase;
    @MockitoBean
    private DeleteStudentUseCase deleteStudentUseCase;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addStudent_ShouldReturnCreatedStudent() throws Exception {
        // Arrange: mock the use case response
        AddStudentResponse response = new AddStudentResponse("1", "John Doe", "john@example.com");
        when(addStudentUseCase.execute(any())).thenReturn(response);

        // Create a request body
        AddStudentRequest request = new AddStudentRequest("John", "Doe", "john@example.com");
        String jsonRequest = objectMapper.writeValueAsString(request);

        // Act & Assert
        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

}

