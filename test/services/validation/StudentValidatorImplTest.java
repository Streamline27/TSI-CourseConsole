package services.validation;

import models.Student;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import services.dao.student.StudentDAO;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Vladislav on 5/18/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class StudentValidatorImplTest {

    private StudentValidatorImpl studentValidator;
    private StudentDAO studentDAO;

    private void assertValid(Student student) {
        assertTrue(studentValidator.isValid(student));
    }

    private void assertNotValid(Student student) {
        assertFalse(studentValidator.isValid(student));
    }

    @Before
    public void setup(){
        studentDAO = Mockito.mock(StudentDAO.class);
        when(studentDAO.read(Mockito.anyString())).thenReturn(null);

        studentValidator = new StudentValidatorImpl(studentDAO);
    }

    @Test
    public void simpleStudentWithAllCredentialsFine_ShouldBeValid() throws Exception {
        Student student = new Student("12345-123456", "Vladislav", "Yakushin");
        assertValid(student);
    }

    @Test
    public void studentWithEmptyFirstName_ShouldNotBeValid(){
        Student student = new Student("12345-123456", "", "Yakushin");
        assertNotValid(student);
    }

    @Test
    public void studentWithNullFirstName_ShouldNotBeValid(){
        Student student = new Student("12345-123456", null, "Yakushin");
        assertNotValid(student);
    }

    @Test
    public void studentWithEmptyLastName_ShouldNotBeValid(){
        Student student = new Student("12345-123456", "Vladislav", "");
        assertNotValid(student);
    }

    @Test
    public void studentWithNullLastName_ShouldNotBeValid(){
        Student student = new Student("12345-123456", "first", null);
        assertNotValid(student);
    }

    @Test
    public void studentWithNoId_ShouldNotBeValid(){
        Student student = new Student("", "First", "Last");
        assertNotValid(student);
    }

    @Test
    public void studentWithNullId_ShouldNotBeValid(){
        Student student = new Student(null, "first", "last");
        assertNotValid(student);
    }

    @Test
    public void studentWithWrongLengthId_ShouldNotBeValid(){
        Student student = new Student("12345-12345", "First", "Last");
        assertNotValid(student);
    }

    @Test
    public void studentWithLettersInId_ShouldNotBeValid(){
        Student student = new Student("AFf45-12345", "First", "Last");
        assertNotValid(student);
    }

    @Test
    public void whenStudentWithIdExists_ShouldNotBeValid(){
        Student student = new Student("00000-000000", "First", "Last");
        when(studentDAO.read("00000-000000")).thenReturn(new Student());

        assertNotValid(student);
    }

}