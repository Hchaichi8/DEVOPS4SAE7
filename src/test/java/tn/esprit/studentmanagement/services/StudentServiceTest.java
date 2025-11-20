package tn.esprit.studentmanagement.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.studentmanagement.entities.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Tests Unitaires pour StudentService")
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    private Student testStudent;

    @BeforeEach
    public void setUp() {
        // Créer un étudiant de test avant chaque test
        testStudent = new Student();
        testStudent.setFirstName("Jean");
        testStudent.setLastName("Dupont");
        testStudent.setEmail("jean.dupont@esprit.tn");
    }

    @Test
    @DisplayName("Test - Récupérer tous les étudiants")
    public void testGetAllStudents() {
        // Given - Setup initial

        // When - Exécuter la méthode
        List<Student> students = studentService.getAllStudents();

        // Then - Vérifier le résultat
        assertNotNull(students, "La liste des étudiants ne doit pas être null");
        assertTrue(students.size() >= 0, "La liste peut être vide mais pas null");
    }

    @Test
    @DisplayName("Test - Sauvegarder un étudiant")
    public void testSaveStudent() {
        // Given - Student créé dans setUp

        // When - Sauvegarder l'étudiant
        Student savedStudent = studentService.saveStudent(testStudent);

        // Then - Vérifier la sauvegarde
        assertNotNull(savedStudent, "L'étudiant sauvegardé ne doit pas être null");
        assertNotNull(savedStudent.getIdStudent(), "L'ID de l'étudiant ne doit pas être null");
        assertEquals("Jean", savedStudent.getFirstName(), "Le prénom doit correspondre");
        assertEquals("Dupont", savedStudent.getLastName(), "Le nom doit correspondre");
        assertEquals("jean.dupont@esprit.tn", savedStudent.getEmail(), "L'email doit correspondre");

        // Nettoyer - Supprimer l'étudiant de test
        studentService.deleteStudent(savedStudent.getIdStudent());
    }

    @Test
    @DisplayName("Test - Récupérer un étudiant par ID")
    public void testGetStudentById() {
        // Given - Créer et sauvegarder un étudiant
        Student savedStudent = studentService.saveStudent(testStudent);
        Long studentId = savedStudent.getIdStudent();

        // When - Récupérer l'étudiant par ID
        Student foundStudent = studentService.getStudentById(studentId);

        // Then - Vérifier la récupération
        assertNotNull(foundStudent, "L'étudiant doit être trouvé");
        assertEquals(studentId, foundStudent.getIdStudent(), "Les IDs doivent correspondre");
        assertEquals("Jean", foundStudent.getFirstName(), "Le prénom doit correspondre");

        // Nettoyer
        studentService.deleteStudent(studentId);
    }

    @Test
    @DisplayName("Test - Supprimer un étudiant")
    public void testDeleteStudent() {
        // Given - Créer et sauvegarder un étudiant
        Student savedStudent = studentService.saveStudent(testStudent);
        Long studentId = savedStudent.getIdStudent();

        // When - Supprimer l'étudiant
        studentService.deleteStudent(studentId);

        // Then - Vérifier que l'étudiant n'existe plus
        Student deletedStudent = studentService.getStudentById(studentId);
        assertNull(deletedStudent, "L'étudiant doit être supprimé et retourner null");
    }

    @Test
    @DisplayName("Test - Étudiant non trouvé")
    public void testGetStudentNotFound() {
        // Given - ID qui n'existe pas
        Long nonExistentId = 999999L;

        // When - Chercher un étudiant avec ID inexistant
        Student student = studentService.getStudentById(nonExistentId);

        // Then - Doit retourner null
        assertNull(student, "Doit retourner null pour un ID inexistant");
    }

    @Test
    @DisplayName("Test - Mise à jour d'un étudiant")
    public void testUpdateStudent() {
        // Given - Créer et sauvegarder un étudiant
        Student savedStudent = studentService.saveStudent(testStudent);
        Long studentId = savedStudent.getIdStudent();

        // When - Modifier l'étudiant
        savedStudent.setFirstName("Pierre");
        savedStudent.setLastName("Martin");
        Student updatedStudent = studentService.saveStudent(savedStudent);

        // Then - Vérifier les modifications
        assertNotNull(updatedStudent, "L'étudiant mis à jour ne doit pas être null");
        assertEquals("Pierre", updatedStudent.getFirstName(), "Le prénom doit être mis à jour");
        assertEquals("Martin", updatedStudent.getLastName(), "Le nom doit être mis à jour");
        assertEquals(studentId, updatedStudent.getIdStudent(), "L'ID doit rester le même");

        // Nettoyer
        studentService.deleteStudent(studentId);
    }
}